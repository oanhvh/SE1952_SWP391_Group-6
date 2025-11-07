<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Contact - Manager</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="../css/style.css" />
    <link rel="stylesheet" href="../css/responsive.css" />
    <link rel="icon" href="../images/fevicon.png" type="image/gif" />
    <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
    <link rel="stylesheet" href="../css/owl.carousel.min.css" />
    <link rel="stylesheet" href="../css/owl.theme.default.min.css" />
    <link rel="stylesheet" href="../css/sidebar.css" />
  </head>
  <body>
    <jsp:include page="includes/header.jsp" />

    <!-- contact section start -->
    <div class="contact_section layout_padding">
      <div class="container">
        <div class="contact_section_2">
          <div class="row">
            <div class="col-md-6">
              <div class="mail_section_1">
                <h1 class="contact_taital">Contact Us</h1>
                <input type="text" class="mail_text_1" placeholder="Name" name="name">
                <input type="email" class="mail_text_1" placeholder="Email" name="email">
                <input type="text" class="mail_text_1" placeholder="Phone Number" name="phone">
                <textarea class="massage-bt" placeholder="Message" rows="5" id="comment" name="message"></textarea>
                <div class="send_bt"><a href="#">SEND</a></div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="map_main">
                <div class="map-responsive">
                  <iframe src="https://www.google.com/maps/embed/v1/place?key=AIzaSyA0s1a7phLN0iaD6-UE7m4qP-z21pH0eSc&amp;q=Eiffel+Tower+Paris+France" width="600" height="360" frameborder="0" style="border:0; width: 100%;" allowfullscreen=""></iframe>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- contact section end -->
    <jsp:include page="includes/footer.jsp" />

    <script src="../js/jquery.min.js"></script>
    <script src="../js/popper.min.js"></script>
    <script src="../js/bootstrap.bundle.min.js"></script>
    <script src="../js/jquery-3.0.0.min.js"></script>
    <script src="../js/plugin.js"></script>
    <script src="../js/role.js?v=2"></script>
    <script src="../js/owl.carousel.js"></script>
    <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
    <script src="../js/login.js"></script>
  </body>
</html>
