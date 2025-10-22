/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


// Giả sử role đã được lưu sau khi đăng nhập
// localStorage.setItem('userRole', 'volunteer');  // hoặc 'organization'
/*document.addEventListener("DOMContentLoaded", function () {
  const userData = localStorage.getItem("currentUser");
  const volunteerSidebar = document.getElementById("volunteerSidebar");
  const orgSidebar = document.getElementById("orgSidebar");
  const toggleButton = document.getElementById("sidebarToggle");

  if (!userData) {
    // Chưa login → quay về login
    window.location.href = "login.html";
    return;
  }

  const user = JSON.parse(userData);

  // Hiển thị đúng sidebar theo role
  if (user.role === "volunteer" && volunteerSidebar) {
    volunteerSidebar.style.display = "block";
  } else if (user.role === "manager" && managerSidebar) {
    orgSidebar.style.display = "block";
  }else if (user.role === "orgstaff" && staffSidebar) {
    orgSidebar.style.display = "block";
  }

  // Toggle sidebar
  toggleButton.addEventListener("click", function () {
    if (user.role === "volunteer") {
      volunteerSidebar.classList.toggle("active");
    } else if (user.role === "manager") {
      orgSidebar.classList.toggle("active");
    }
    else if (user.role === "orgstaff") {
      orgSidebar.classList.toggle("active");
    }
  });
});
*/




