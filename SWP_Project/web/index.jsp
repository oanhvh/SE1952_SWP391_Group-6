<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Event" %>
<%@ page import="dao.EventDAO" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Doni Charity - Manager</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <link rel="stylesheet" href="css/responsive.css" />
    <link rel="icon" href="images/fevicon.png" type="image/gif" />
    <link rel="stylesheet" href="css/jquery.mCustomScrollbar.min.css" />
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
    <link rel="stylesheet" href="css/owl.carousel.min.css" />
    <link rel="stylesheet" href="css/owl.theme.default.min.css" />
    <link rel="stylesheet" href="css/sidebar.css" />
    <link rel="stylesheet" type="text/css" href="css/events-homepage.css"/>

  </head>
  <body>
      
    <div class="header_section">
        
      </div><nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand"><a href="index.html"><img src="images/logo.png"></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
               <ul class="navbar-nav mr-auto">
                  <li class="nav-item active">
                     <a class="nav-link" href="index.html">Home</a>
                  </li>
                  <li class="nav-item">
                     <a class="nav-link" href="about.html">About</a>
                  </li>
                  <li class="nav-item">
                     <a class="nav-link" href="donate.html">Donate</a>
                  </li>
                  <li class="nav-item">
                     <a class="nav-link" href="news.html">News</a>
                  </li>
                  <li class="nav-item">
                     <a class="nav-link" href="contact.html">Contact Us</a>
                  </li>
                  <li class="nav-item">
                     <a class="nav-link" href="mission.html">Our Mission</a>
                  </li>
                  <li class="nav-item">
                     <a class="nav-link" href="login.jsp">Login</a>
                  </li>
                 
               </ul>
               <form class="form-inline my-2 my-lg-0">
                  <div class="search_icon"><a href="#"><img src="images/search-icon.png"></a></div>
                  <button class="donate_btn" type="submit">Donate Now</button>
               </form>
            </div>
         </nav>
   
    <!-- banner section end -->
    
    <!-- banner section start -->
    <div class="banner_section layout_padding">
       <div class="container">
          <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
             <ol class="carousel-indicators">
                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
             </ol>
             <div class="carousel-inner">
                <div class="carousel-item active">
                   <div class="row">
                      <div class="col-sm-12">
                         <h1 class="banner_taital">Help Poor Child</h1>
                         <p class="banner_text">Every child deserves a chance to learn, grow, and dream. Your small act of kindness can bring food, education, and hope to poor children in need. </p>
                         
                      </div>
                   </div>
                </div>
                <div class="carousel-item">
                   <div class="col-sm-12">
                      <h1 class="banner_taital">Help Patient In Need</h1>
                      <p class="banner_text">Many patients struggle to afford treatment. Your support can help provide medicine, care, and a second chance at life for those in need. </p>
                      
                   </div>
                </div>
                <div class="carousel-item">
                   <div class="col-sm-12">
                      <h1 class="banner_taital">Help People Affected By Natural Disasters</h1>
                      <p class="banner_text">Natural disasters destroy homes and lives in moments. Together, we can provide shelter, food, and comfort to families rebuilding from tragedy.</p>
                      
                   </div>
                </div>
                <div class="carousel-item">
                   <div class="col-sm-12">
                      <h1 class="banner_taital">Help The Environment</h1>
                      <p class="banner_text">Protecting the planet starts with us. Join our mission to plant trees, reduce waste, and preserve nature for future generations. </p>
                      
                   </div>
                </div>
                <div class="carousel-item">
                   <div class="col-sm-12">
                      <h1 class="banner_taital">Help Everyone</h1>
                      <p class="banner_text">Together, we can create a better world for all—where kindness, compassion, and equality guide every action we take.</p>
                      
                   </div>
                </div>
             </div>
          </div>
       </div>
       <div class="donation_box">
          
          
       </div>
       
    </div>
    <!-- banner section end -->

    <!-- about section start -->
    <div class="about_section layout_padding">
         <div class="container">
            <div class="row">
               <div class="col-sm-8">
                  <h2 class="about_taital">About Charity</h2>
                  <p class="about_text">Charity is the heart of humanity— a bridge that connects compassion with action, and transforms kindness into real change. Our charity organization was founded with a simple yet powerful mission: to bring hope, opportunity, and dignity to those who need it most </p>
                  <div class="readmore_bt"><a href="about.html">Read more</a></div>
               </div>
               <div class="col-sm-4">
                  <div class="about_img"><img src="images/about-img.png"></div>
               </div>
            </div>
         </div>
      </div>
    <!-- about section end -->
  
    <%--event--%>
<div class="featured-cause">
    <h2>EVENTS</h2>
    <p class="description"></p>

<%
    EventDAO dao = new EventDAO();
    List<Event> allEvents = dao.getAllEvents1();
    if(allEvents != null && !allEvents.isEmpty()) {
        int count = 0;
        for(Event e : allEvents) {
            count++;
%>
    <div class="cause-item" <%= (count > 2 ? "style='display:none'" : "") %>>
        <div class="cause-image">
            <img src="<%= e.getImage() %>" alt="<%= e.getEventName() %>">
            <div class="cause-date">
                <span class="day"><%= e.getStartDate().getDayOfMonth() %></span>
                <span class="month"><%= e.getStartDate().getMonth().toString().substring(0,3) %></span>
            </div>
        </div>
        <div class="cause-info">
            <h3><%= e.getEventName() %></h3>
            <p><%= e.getDescription() %></p>
            
            <div class="buttons">
                <a href="#" class="read-more">READ MORE</a>
                <a href="#" class="donate-now">DONATE NOW</a>
            </div>
        </div>
    </div>
<%
        } // end for
%>
    <% if(allEvents.size() > 3) { %>
<div style="text-align:center;">
    <a id="showMoreBtn" href="news.html" 
       >
        More Events
    </a>
</div>
    <% } %>
<%
    } else {
%>
    <p>Chưa có sự kiện nào.</p>
<%
    }
%>
</div>
      <!-- events section end -->
      <!-- donate section start -->
      <div class="donate_section layout_padding">
         <div class="container">
            <h1 class="donate_taital">Donat pepole Says </h1>
            <div class="donate_taital_main">
               <div id="main_slider" class="carousel slide" data-ride="carousel">
                  <div class="carousel-inner">
                     <div class="carousel-item active">
                        <div class="donate_left">
                           <div class="client_img"><img src="images/client-img.png"></div>
                        </div>
                        <div class="donate_right">
                           <h3 class="client_name_text">Mardo Merk</h3>
                           <p class="dummy_text">If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefinedIf you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined</p>
                        </div>
                     </div>
                     <div class="carousel-item">
                        <div class="donate_left">
                           <div class="client_img"><img src="images/client-img.png"></div>
                        </div>
                        <div class="donate_right">
                           <h3 class="client_name_text">Mardo Merk</h3>
                           <p class="dummy_text">If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefinedIf you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined</p>
                        </div>
                     </div>
                     <div class="carousel-item">
                        <div class="donate_left">
                           <div class="client_img"><img src="images/client-img.png"></div>
                        </div>
                        <div class="donate_right">
                           <h3 class="client_name_text">Mardo Merk</h3>
                           <p class="dummy_text">If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefinedIf you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined</p>
                        </div>
                     </div>
                  </div>
                  <a class="carousel-control-next" href="#main_slider" role="button" data-slide="next">
                  <i class="fa fa-angle-right"></i>
                  </a>
                  <a class="carousel-control-prev" href="#main_slider" role="button" data-slide="prev">
                  <i class="fa fa-angle-left"></i>
                  </a>
               </div>
            </div>
         </div>
      </div>
    <div class="footer_section layout_padding">
         <div class="container">
            <div class="row">
               <div class="col-sm-6 col-md-6 col-lg-3">
                  <div class="footer_logo"><img src="images/footer-logo.png"></div>
               </div>
               <div class="col-sm-6 col-md-6 col-lg-3">
                  <h4 class="footer_taital">NAVIGATION</h4>
                  <div class="footer_menu_main">
                     <div class="footer_menu_left">
                        <div class="footer_menu">
                           <ul>
                              <li><a href="index_1.html">Home</a></li>
                              <li><a href="donate.html">Donate</a></li>
                              <li><a href="contact.html">Contact us</a></li>
                           </ul>
                        </div>
                     </div>
                     <div class="footer_menu_right">
                        <div class="footer_menu">
                           <ul>
                              <li><a href="about.html">About</a></li>
                              <li><a href="news.html">News</a></li>
                              <li><a href="mission.html">Our Mission</a></li>
                           </ul>
                        </div>
                     </div>
                  </div>
               </div>
               
               <div class="col-sm-6 col-md-6 col-lg-3">
                  <h4 class="footer_taital">address</h4>
                  <p class="footer_text">Ave NW, Washington</p>
                  <p class="footer_text">+01 1234567890</p>
                  <p class="footer_text">demo@gmail.com</p>
               </div>
            </div>
            <div class="footer_section_2">
               <div class="row">
                  <div class="col-sm-4 col-md-4 col-lg-3">
                     
                  </div>
                  
               </div>
            </div>
         </div>
      </div>
    <script src="js/jquery.min.js"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/jquery-3.0.0.min.js"></script>
    <script src="js/plugin.js"></script>
    <script src="js/role.js?v=2"></script>
    <script src="js/owl.carousel.js"></script>
    <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
    <script src="js/login.js"></script>
    <script>
function showMore() {
    var hiddenEvents = document.querySelectorAll(".cause-item[style*='display:none']");
    hiddenEvents.forEach(function(item){
        item.style.display = "flex";
    });
    document.getElementById("showMoreBtn").style.display = "none";
}
</script>
  </body>
</html>