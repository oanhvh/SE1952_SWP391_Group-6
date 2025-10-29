<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Activation Codes</title>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="../css/sidebar.css" />
        <link rel="stylesheet" href="../css/activation.css" />
    </head>
    <body class="activation-page">
        <jsp:include page="includes/header.jsp" />

        <div class="container py-4">
            <div class="d-flex align-items-center justify-content-between mb-3">
                <h3 class="mb-0">Activation Codes</h3>
                <div>
                    <button id="btnGenerate" class="btn btn-primary">Generate Code</button>
                    <button id="btnReload" class="btn btn-outline-secondary ml-2">Reload</button>
                </div>
            </div>

            <div id="lastCodeWrap" class="mb-3" style="display:none;">
                <span class="mr-2">Last generated:</span>
                <span id="lastCode" class="code-badge"></span>
            </div>

            <div class="card">
                <div class="card-header">Codes</div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table mb-0">
                            <thead class="thead-light">
                                <tr>
                                    <th style="width:200px;">Code</th>
                                    <th style="width:160px;">Status</th>
                                    <th style="min-width:180px;">Created At</th>
                                    <th style="width:120px;">Actions</th>
                                </tr>
                            </thead>
                            <tbody id="codeTableBody">
                                <tr><td colspan="4" class="text-center text-muted py-4">No codes yet</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="includes/footer.jsp" />

        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/role.js?v=2"></script>
        <script>
            function statusLabel(used, createdAtMs) {
                if (used)
                    return {text: 'Activated', cls: 'status-used'};
                const created = (createdAtMs && !isNaN(createdAtMs)) ? new Date(Number(createdAtMs)) : null;
                if (!created)
                    return {text: 'Not yet activated', cls: 'status-pending'};
                const diffH = (Date.now() - created.getTime()) / (1000 * 60 * 60);
                if (diffH < 24)
                    return {text: 'New', cls: 'status-new'};
                return {text: 'Not yet activated', cls: 'status-pending'};
            }

            async function fetchList() {
                const resp = await fetch('<%= request.getContextPath() %>/manager/activation-code');
                if (!resp.ok)
                    throw new Error('Load failed');
                return resp.json();
            }
            async function generateCode() {
                const resp = await fetch('<%= request.getContextPath() %>/manager/activation-code', {method: 'POST'});
                if (!resp.ok)
                    throw new Error('Generate failed');
                return resp.json();
            }
            async function deleteCode(id) {
                const url = '<%= request.getContextPath() %>/manager/activation-code?id=' + encodeURIComponent(id);
                const resp = await fetch(url, {method: 'DELETE'});
                if (!resp.ok)
                    throw new Error('Delete failed');
                return resp.json();
            }

            async function render() {
                const tbody = document.getElementById('codeTableBody');
                tbody.innerHTML = '<tr><td colspan="4" class="text-center text-muted py-4">Loading...</td></tr>';
                try {
                    const list = await fetchList();
                    tbody.innerHTML = '';
                    if (!list || list.length === 0) {
                        tbody.innerHTML = '<tr><td colspan="4" class="text-center text-muted py-4">No codes yet</td></tr>';
                        return;
                    }
                    list.sort((a, b) => (Number(b.createdAtMs) || 0) - (Number(a.createdAtMs) || 0));
                    for (const item of list) {
                        const tr = document.createElement('tr');
                        const st = statusLabel(item.used, item.createdAtMs);
                        tr.innerHTML =
                                '<td><span class="code-badge">' + item.code + '</span></td>' +
                                '<td><span class="status-badge ' + st.cls + '">' + st.text + '</span></td>' +
                                '<td>' + (item.createdAtMs ? new Date(Number(item.createdAtMs)).toLocaleString() : '') + '</td>' +
                                '<td>' +
                                '<button class="btn btn-sm btn-outline-danger" data-action="delete" data-id="' + item.codeId + '">Delete</button>' +
                                '</td>';
                        tbody.appendChild(tr);
                    }
                } catch (e) {
                    tbody.innerHTML = '<tr><td colspan="4" class="text-danger text-center py-4">Load failed</td></tr>';
                }
            }

            document.addEventListener('DOMContentLoaded', () => {
                const btnGenerate = document.getElementById('btnGenerate');
                const btnReload = document.getElementById('btnReload');
                const lastWrap = document.getElementById('lastCodeWrap');
                const lastCode = document.getElementById('lastCode');

                render();

                btnGenerate.addEventListener('click', async () => {
                    btnGenerate.disabled = true;
                    try {
                        await generateCode();
                        const list = await fetchList();
                        if (list && list.length) {
                            list.sort((a, b) => (Number(b.createdAtMs) || 0) - (Number(a.createdAtMs) || 0));
                            lastCode.textContent = list[0].code;
                            lastWrap.style.display = 'inline-block';
                        }
                        await render();
                    } catch (e) {
                        alert('Unable to generate code, please try again');
                    } finally {
                        btnGenerate.disabled = false;
                    }
                });

                btnReload.addEventListener('click', () => render());

                document.getElementById('codeTableBody').addEventListener('click', async (e) => {
                    const btn = e.target.closest('button');
                    if (!btn)
                        return;
                    const action = btn.getAttribute('data-action');
                    const id = btn.getAttribute('data-id');
                    if (action === 'delete') {
                        if (confirm('Delete this code?')) {
                            try {
                                await deleteCode(id);
                                await render();
                            } catch (e) {
                                alert('Delete failed');
                            }
                        }
                    }
                });
            });
        </script>
    </body>
</html>
