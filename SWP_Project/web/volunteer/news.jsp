<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>News - Manager</title>
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

    <!-- news section start -->
    <div class="news_section layout_padding">
      <div class="container">
        <div class="row">
          <div class="col-sm-12">
            <h1 class="news_taital">FEATURED CAUSE</h1>
            <p class="news_text">going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. </p>
          </div>
        </div>
        <div class="news_section_2">
          <div class="row">
            <div class="col-md-6">
              <div class="news_img"><img src="../images/news-img.png"></div>
            </div>
            <div class="col-md-6">
              <h1 class="give_taital">GIVE EDUCATION</h1>
              <p class="ipsum_text">variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by<br> injected humour, or randomised v<br> ariations of passages of Lorem Ipsum <br> available, but the majority have suffered alteration in some form, by injected humour, or randomised </p>
              <h5 class="raised_text">Raised: $60,010     <span class="goal_text">Goal: $70,000</span></h5>
              <div class="donate_btn_main">
                <div class="readmore_btn"><a href="#">Read More</a></div>
                <div class="readmore_btn_1"><a href="donate.jsp">Donate Now</a></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- news section end -->

    <jsp:include page="includes/footer.jsp" />

    <script src="../js/jquery.min.js"></script>
    <script src="../js/popper.min.js"></script>
    <script src="../js/bootstrap.bundle.min.js"></script>
    <script src="../js/jquery-3.0.0.min.js"></script>
    <script src="../js/plugin.js"></script>
    <script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="../js/custom.js"></script>
    <script src="../js/owl.carousel.js"></script>
    <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
  </body>
</html>
