<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="entity.Event"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Event List</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="../css/owl.carousel.min.css" />
        <link rel="stylesheet" href="../css/owl.theme.default.min.css" />
        <link rel="stylesheet" href="../css/sidebar.css" />
        <style>
            .event-image {
                width: 100%;
                height: 250px;
                object-fit: cover;
            }
            .card {
                transition: transform 0.3s ease;
                height: 100%;
            }
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 16px rgba(0,0,0,0.2) !important;
            }
            .status-badge {
                display: inline-block;
                padding: 5px 12px;
                border-radius: 20px;
                font-size: 0.85rem;
                font-weight: 600;
            }
            .status-Pending {
                background-color: #ffc107;
                color: #000;
            }
            .status-Active {
                background-color: #28a745;
                color: #fff;
            }
            .status-Completed {
                background-color: #6c757d;
                color: #fff;
            }
            .card-text {
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
                overflow: hidden;
                text-overflow: ellipsis;
                min-height: 4.5em;
            }
            .btn-actions {
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
            }
            .no-image {
                width: 100%;
                height: 250px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 1.5rem;
            }
            #searchEvent {
                transition: all 0.3s ease;
            }
            #searchEvent:focus {
                box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
            }
            
            /* CUSTOM MODAL - NOT USING BOOTSTRAP MODAL */
            .custom-modal-overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                z-index: 9999;
                overflow-y: auto;
            }
            
            .custom-modal-overlay.active {
                display: flex;
                align-items: center;
                justify-content: center;
            }
            
            .custom-modal-content {
                background: white;
                border-radius: 8px;
                width: 90%;
                max-width: 500px;
                margin: 20px;
                box-shadow: 0 5px 15px rgba(0,0,0,0.3);
            }
            
            .custom-modal-header {
                padding: 15px 20px;
                border-bottom: 1px solid #dee2e6;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            
            .custom-modal-header h5 {
                margin: 0;
                font-size: 1.25rem;
            }
            
            .custom-modal-close {
                background: none;
                border: none;
                font-size: 1.5rem;
                cursor: pointer;
                padding: 0;
                width: 30px;
                height: 30px;
                line-height: 1;
                opacity: 0.5;
            }
            
            .custom-modal-close:hover {
                opacity: 1;
            }
            
            .custom-modal-body {
                padding: 20px;
            }
            
            .custom-modal-footer {
                padding: 15px 20px;
                border-top: 1px solid #dee2e6;
                display: flex;
                justify-content: flex-end;
                gap: 10px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <div class="news_section layout_padding">
            <div class="container">
                <div class="row mb-4 align-items-center">
                    <div class="col-md-8">
                        <h1 class="news_taital mb-0">EVENT REVIEW LIST</h1>
                        <p class="news_text mb-0">Review events submitted by staff for approval or denial.</p>
                    </div>
                    <div class="col-md-4 text-right">
                        <div class="input-group">
                            <input type="text" id="searchEvent" class="form-control rounded-pill" placeholder="🔍 Search events...">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <c:forEach var="event" items="${eventList}">
                        <div class="col-md-6 col-lg-4 mb-4">
                            <div class="card shadow-sm border-0">
                                <c:choose>
                                    <c:when test="${not empty event.image}">
                                        <img src="${pageContext.request.contextPath}/${event.image}" 
                                             class="card-img-top event-image" 
                                             alt="${event.eventName}"
                                             onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default-event.jpg';">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-image">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                                <div class="card-body">
                                    <h5 class="card-title">${event.eventName}</h5>
                                    <p class="card-text text-muted">${event.description}</p>

                                    <div class="mb-2">
                                        <small><strong><i class="fa fa-map-marker"></i> Location:</strong></small>
                                        <p class="mb-1">${event.location}</p>
                                    </div>

                                    <div class="mb-2">
                                        <small><strong><i class="fa fa-clock-o"></i> Date:</strong></small>
                                        <p class="mb-1">${event.startDate} - ${event.endDate}</p>
                                    </div>

                                    <div class="mb-3">
                                        <small><strong>Status:</strong></small><br>
                                        <span class="status-badge status-${event.status}">${event.status}</span>
                                    </div>

                                    <div class="btn-actions">
                                        <a href="${pageContext.request.contextPath}/manager/event?action=detail&id=${event.eventID}" 
                                           class="btn btn-primary btn-sm flex-fill">
                                            <i class="fa fa-eye"></i> View Details
                                        </a>

                                        <c:if test="${not empty sessionScope.role && sessionScope.role == 'Manager'}">
                                            <form method="post" action="${pageContext.request.contextPath}/manager/event" style="display:inline;" onsubmit="return confirm('Are you sure you want to approve this event?');">
                                                <input type="hidden" name="action" value="approve"/>
                                                <input type="hidden" name="id" value="${event.eventID}"/>
                                                <button type="submit" class="btn btn-warning btn-sm">
                                                    <i class="fa fa-check"></i> Approve
                                                </button>
                                            </form>
                                            <!-- Nút Deny mở CUSTOM modal -->
                                            <button type="button" class="btn btn-danger btn-sm btn-deny-custom" 
                                                    data-event-id="${event.eventID}" 
                                                    data-event-name="${event.eventName}">
                                                <i class="fa fa-times"></i> Deny
                                            </button>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <c:if test="${empty eventList}">
                    <div class="row">
                        <div class="col-12">
                            <div class="alert alert-info text-center" role="alert">
                                <i class="fa fa-info-circle fa-3x mb-3"></i>
                                <h4>No events pending review at the moment.</h4>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- CUSTOM MODAL (NOT BOOTSTRAP) -->
        <div id="customDenyModal" class="custom-modal-overlay">
            <div class="custom-modal-content">
                <form method="post" action="${pageContext.request.contextPath}/manager/event" id="denyForm">
                    <div class="custom-modal-header">
                        <h5 id="modalEventName">Deny Event</h5>
                        <button type="button" class="custom-modal-close" onclick="closeDenyModal()">&times;</button>
                    </div>
                    <div class="custom-modal-body">
                        <input type="hidden" name="action" value="deny"/>
                        <input type="hidden" name="id" id="modalEventId"/>
                        <div class="form-group">
                            <label for="denyReason">Reason for denial:</label>
                            <textarea class="form-control" name="reason" id="denyReason" rows="4" required></textarea>
                        </div>
                    </div>
                    <div class="custom-modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="closeDenyModal()">Cancel</button>
                        <button type="submit" class="btn btn-danger">Submit Denial</button>
                    </div>
                </form>
            </div>
        </div>

        <jsp:include page="includes/footer.jsp" />

        <!-- Scripts - Load jQuery ONCE -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="../js/plugin.js"></script>
        <script src="../js/role.js?v=2"></script>
        <script src="../js/owl.carousel.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
        <script src="../js/login.js"></script>
        <script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="../js/custom.js"></script>
        
        <script>
            // CUSTOM MODAL FUNCTIONS - NO BOOTSTRAP MODAL
            function openDenyModal(eventId, eventName) {
                document.getElementById('modalEventId').value = eventId;
                document.getElementById('modalEventName').textContent = 'Deny Event: ' + eventName;
                document.getElementById('denyReason').value = '';
                document.getElementById('customDenyModal').classList.add('active');
                document.body.style.overflow = 'hidden';
            }
            
            function closeDenyModal() {
                document.getElementById('customDenyModal').classList.remove('active');
                document.body.style.overflow = '';
                document.getElementById('denyReason').value = '';
            }
            
            // Event listeners
            document.addEventListener('DOMContentLoaded', function() {
                // Deny buttons
                document.querySelectorAll('.btn-deny-custom').forEach(function(btn) {
                    btn.addEventListener('click', function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        var eventId = this.getAttribute('data-event-id');
                        var eventName = this.getAttribute('data-event-name');
                        openDenyModal(eventId, eventName);
                    });
                });
                
                // Close on overlay click
                document.getElementById('customDenyModal').addEventListener('click', function(e) {
                    if (e.target === this) {
                        closeDenyModal();
                    }
                });
                
                // Close on ESC key
                document.addEventListener('keydown', function(e) {
                    if (e.key === 'Escape') {
                        closeDenyModal();
                    }
                });
                
                // Search functionality
                document.getElementById('searchEvent').addEventListener('keyup', function() {
                    var keyword = this.value.toLowerCase();
                    document.querySelectorAll('.card').forEach(function(card) {
                        var name = card.querySelector('.card-title').textContent.toLowerCase();
                        card.parentElement.style.display = name.includes(keyword) ? '' : 'none';
                    });
                });
            });
        </script>
    </body>
</html>