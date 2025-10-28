/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


// Giả sử role đã được lưu sau khi đăng nhập
// localStorage.setItem('userRole', 'volunteer');  // hoặc 'organization'
document.addEventListener("DOMContentLoaded", function () {
  const userData = localStorage.getItem("currentUser");
  const volunteerSidebar = document.getElementById("volunteerSidebar");
  const orgSidebar = document.getElementById("orgSidebar");
  const managerSidebar = document.getElementById("managerSidebar");
  const toggleButton = document.getElementById("sidebarToggle");

  // Luôn gắn sự kiện toggle cho nút ☰, không phụ thuộc vào localStorage
  if (toggleButton) {
    toggleButton.addEventListener("click", function () {
      if (managerSidebar) {
        managerSidebar.classList.toggle("active");
        return;
      }
      if (volunteerSidebar) {
        volunteerSidebar.classList.toggle("active");
        return;
      }
      if (orgSidebar) {
        orgSidebar.classList.toggle("active");
      }
    });
  }

  if (!userData) {
    // Không tự redirect nữa; trang đã được bảo vệ bằng session server-side
    // để tránh đẩy nhầm sang /manager/login.jsp do đường dẫn tương đối hoặc cache JS.
    // Có thể hiển thị sidebar tối thiểu hoặc ẩn hoàn toàn nếu cần.
    // Không return sớm nữa để nút ☰ vẫn hoạt động.
    // Chỉ dừng phần xử lý phụ thuộc user.
    return; // dừng phần dưới (phụ thuộc user), toggle phía trên vẫn chạy
  }

  const user = JSON.parse(userData);

  // Hiển thị đúng sidebar theo role
  if (user.role === "volunteer" && volunteerSidebar) {
    volunteerSidebar.style.display = "block";
  } else if (user.role === "manager" && managerSidebar) {
    managerSidebar.style.display = "block";
  }else if (user.role === "staff" && staffSidebar) {
    orgSidebar.style.display = "block";
  }

  // (listener toggle đã gắn ở trên)
});





