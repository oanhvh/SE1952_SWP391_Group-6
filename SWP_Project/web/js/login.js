/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


//đăng ký
// ====== XỬ LÝ SIGN UP ======
/*const signupForm = document.getElementById('signupForm');

if (signupForm) {
  signupForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const role = document.getElementById('role').value;
    const fullName = document.getElementById('name').value;
    const phone = document.getElementById('phone').value;
    const age = document.getElementById('age').value;
    const email = document.getElementById('email').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const confirm = document.getElementById('confirm').value;

    if (password !== confirm) {
      alert('Passwords do not match!');
      return;
    }

    // Kiểm tra username có bị trùng không
    let users = JSON.parse(localStorage.getItem('users')) || [];
    const existed = users.find(u => u.username === username);
    if (existed) {
      alert('Username already exists!');
      return;
    }

    const newUser = {
      role,
      fullName,
      phone,
      age,
      email,
      username,
      password,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    };

    users.push(newUser);
    localStorage.setItem('users', JSON.stringify(users));

    alert('Sign up successful!');
    window.location.href = 'login.html';
  });
}


//đăng nhập
//const loginForm = document.getElementById('loginForm');
if (loginForm) {
  loginForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const users = JSON.parse(localStorage.getItem('users')) || [];
    const user = users.find(u => u.username === username && u.password === password);

    if (user) {
      localStorage.setItem('currentUser', JSON.stringify(user));
      alert('Login success!');
      window.location.href = 'index.html'; // chuyển về trang chủ hoặc dashboard
    } else {
      alert('Invalid username or password');
    }
  });
}
*/
// ====== TẠO TÀI KHOẢN MẶC ĐỊNH ======
const defaultUsers = [
  {
    username: 'admin',
    password: '123456',
    role: 'organization',
    fullName: 'Admin User',
    email: 'admin@example.com',
    createdAt: new Date().toISOString(),
  },
  {
    username: 'volunteer',
    password: '123456',
    role: 'volunteer',
    fullName: 'Volunteer User',
    email: 'volunteer@example.com',
    createdAt: new Date().toISOString(),
  }
];

let users = JSON.parse(localStorage.getItem('users'));
if (!users || users.length === 0) {
  localStorage.setItem('users', JSON.stringify(defaultUsers));
}

// ====== XỬ LÝ LOGIN ======
document.addEventListener("DOMContentLoaded", function () {
  const loginForm = document.getElementById("loginForm");

  loginForm.addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // 🧠 Đây chỉ là ví dụ, bạn có thể thay bằng kiểm tra thật từ DB/backend
    const dummyUsers = [
      { username: "volunteer1", password: "123", role: "volunteer" },
      { username: "org1", password: "123", role: "organization" }
    ];

    const foundUser = dummyUsers.find(
      (u) => u.username === username && u.password === password
    );

    if (foundUser) {
      // Lưu thông tin user vào localStorage
      localStorage.setItem("currentUser", JSON.stringify(foundUser));

      alert("Đăng nhập thành công!");

      // 👉 Thêm dòng này để chuyển về index.html
      window.location.href = "index.html";
    } else {
      alert("Sai tài khoản hoặc mật khẩu!");
    }
  });
});



