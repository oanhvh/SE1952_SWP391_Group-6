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
        <style>
            /* Ensure pagination numbers are visible regardless of global styles */
            .pagination .page-link {
                color: #0d6efd !important;
            }
            .pagination .page-item.disabled .page-link {
                color: #6c757d !important;
                pointer-events: none;
            }
            .pagination .page-item.active .page-link {
                background-color: #e91e63;
                border-color: #e91e63;
                color: #fff !important;
            }
        </style>
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
                                    <th style="width:180px;">Creator</th>
                                    <th style="min-width:180px;">Created At</th>
                                    <th style="width:120px;">Actions</th>
                                </tr>
                            </thead>
                            <tbody id="codeTableBody">
                                <tr><td colspan="5" class="text-center text-muted py-4">No codes yet</td></tr>
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
                if (diffH < 3)
                    return {text: 'New', cls: 'status-new'};
                if (diffH >= 24)
                    return {text: 'Expired', cls: 'status-expired'};
                return {text: 'Not yet activated', cls: 'status-pending'};
            }

            let currentPage = 1;
            let pageSize = 50;
            const urlParams = new URLSearchParams(window.location.search);
            let currentStatus = (urlParams.get('status') || '').toLowerCase();
            async function fetchList(page = 1, size = pageSize) {
                const url = '<%= request.getContextPath() %>/manager/activation-code?page=1&pageSize=' + encodeURIComponent(size);
                const resp = await fetch(url, {cache: 'no-store'});
                if (!resp.ok)
                    throw new Error('Load failed');
                return resp.json();
            }
            async function generateCode() {
                const resp = await fetch('<%= request.getContextPath() %>/manager/activation-code', {method: 'POST'});
                if (!resp.ok) {
                    const msg = await resp.text();
                    throw new Error(msg || 'Generate failed');
                }
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
                tbody.innerHTML = '<tr><td colspan="5" class="text-center text-muted py-4">Loading...</td></tr>';
                try {
                    const result = await fetchList(currentPage, pageSize);
                    let items = result && Array.isArray(result.items) ? result.items : (Array.isArray(result) ? result : []);
                    if (currentStatus) {
                        items = items.filter(it => {
                            const st = statusLabel(it.used, it.createdAtMs).text.toLowerCase();
                            return st === currentStatus;
                        });
                    }
                    tbody.innerHTML = '';
                    if (!items || items.length === 0) {
                        tbody.innerHTML = '<tr><td colspan="5" class="text-center text-muted py-4">No codes yet</td></tr>';
                        return;
                    }
                    items.sort((a, b) => (Number(b.createdAtMs) || 0) - (Number(a.createdAtMs) || 0));
                    for (const item of items) {
                        const tr = document.createElement('tr');
                        const st = statusLabel(item.used, item.createdAtMs);
                        tr.innerHTML =
                                '<td><span class="code-badge">' + item.code + '</span></td>' +
                                '<td><span class="status-badge ' + st.cls + '">' + st.text + '</span></td>' +
                                '<td>' + (item.creatorName ? item.creatorName : '') + '</td>' +
                                '<td>' + (item.createdAtMs ? new Date(Number(item.createdAtMs)).toLocaleString() : '') + '</td>' +
                                '<td>' +
                                '<button class="btn btn-sm btn-outline-danger" data-action="delete" data-id="' + item.codeId + '">Delete</button>' +
                                '</td>';
                        tbody.appendChild(tr);
                    }
                    // no pager
                } catch (e) {
                    tbody.innerHTML = '<tr><td colspan="5" class="text-danger text-center py-4">Load failed</td></tr>';
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
                        const result = await fetchList(1, pageSize);
                        const items = result && Array.isArray(result.items) ? result.items : [];
                        if (items && items.length) {
                            items.sort((a, b) => (Number(b.createdAtMs) || 0) - (Number(a.createdAtMs) || 0));
                            lastCode.textContent = items[0].code;
                            lastWrap.style.display = 'inline-block';
                        }
                        await render();
                    } catch (e) {
                        alert('Unable to generate code, please try again.\n' + (e && e.message ? e.message : ''));
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
