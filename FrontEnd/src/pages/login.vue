<script>
export default {
  name: "LoginPage",
  data() {
    return {
      username: "",
      password: "",
      userId: "",
      errorMessage: "",
      showReset: false,
      resetErrorMessage: "",
      resetForm: {
        username: "",
        email: "",
        newPassword: "",
        confirmPassword: ""
      }
    };
  },
  methods: {
    async login() {
      try {
        const res = await fetch("http://localhost:8083/api/user/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            username: this.username,
            password: this.password
          })
        });

        const data = await res.json();

        if (!res.ok) {
          throw new Error(data.message || "Sai tài khoản hoặc mật khẩu!");
        }
        localStorage.setItem("username", data.username);
        localStorage.setItem("role", data.role);
        localStorage.setItem("userId", data.id);
        // alert("Đăng nhập thành công! Xin chào " + this.username);

        if (data.role === "ADMIN") {
          this.$router.push("/adminHome");
        } else {
          this.$router.push("/home");
        }
      } catch (err) {
        this.errorMessage = err.message;
      }
    },

    async resetPassword() {
      if (this.resetForm.newPassword !== this.resetForm.confirmPassword) {
        this.resetErrorMessage = "Mật khẩu xác nhận không khớp.";
        return;
      }
      if(this.resetForm.newPassword.length < 8){
        this.resetErrorMessage = "Mật khẩu mới phải có ít nhất 8 ký tự.";
        return;
      }
      if (!/[!@#$%^&*(),.?":{}|<>]/.test(this.resetForm.newPassword)) {
        this.resetErrorMessage = "Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt.";
        return;
      }
        try{
          const res = await fetch("http://localhost:8083/api/user/reset-password", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
              username: this.resetForm.username,
              email: this.resetForm.email,
              password: this.resetForm.newPassword,
            })
          })
        
      const data = await res.json();
      if(!res.ok){
        throw new Error(data.message || "Không thể đặt lại mật khẩu");
      }else{
        this.resetErrorMessage = "";
        this.$router.push("/login");
      }
    }catch(err){
      this.resetErrorMessage = err.message;
    }
      
    }
  }
};
</script>
<template>
  <div class="wrapper">
    <div class="container">
      <!-- Bên trái -->
      <div class="left">
        <div class="logo-title">
          <img
            src="https://cdn.dribbble.com/userupload/28456975/file/original-923692d84cf3a6905b017e91981ed0af.gif"
            alt="Logo"
          />
          <h1>ABYSS - Smart Parking System</h1>
        </div>
        <p>
          Hệ thống bãi đỗ xe thông minh giúp bạn quản lý việc gửi xe dễ dàng hơn.<br />
          ✅ Theo dõi tình trạng bãi đỗ xe theo thời gian thực.<br />
          ✅ Đặt chỗ trước để đảm bảo có chỗ đỗ.<br />
          ✅ Thanh toán nhanh chóng, an toàn.<br />
          ✅ Báo cáo chi tiết cho người quản lý.
        </p>
      </div>

      <!-- Bên phải -->
      <div class="right">
        <!-- Form đăng nhập -->
        <div v-if="!showReset" id="login-form">
          <h2>Đăng nhập</h2>
          <form @submit.prevent="login">
            <p>
              <label for="username">Tên đăng nhập</label>
              <input v-model="username" type="text" id="username" required />
            </p>
            <p>
              <label for="password">Mật khẩu</label>
              <input v-model="password" type="password" id="password" required />
            </p>
            <button type="submit">Đăng nhập</button>
          </form>
          <div v-if="errorMessage" style="color:red; margin-top:10px;">
            {{ errorMessage }}
          </div>

          <a href="#" class="toggle-link" @click.prevent="showReset = true">
            Quên mật khẩu?
          </a>
          <router-link to="/register" class="toggle-link">Bạn chưa có tài khoản? Đăng ký</router-link>
        </div>

        <!-- Form reset mật khẩu -->
        <div v-else id="reset-form">
          <h2>Đặt lại mật khẩu</h2>
          <form @submit.prevent="resetPassword">
            <p>
              <label for="reset-username">Tên đăng nhập</label>
              <input v-model="resetForm.username" type="text" id="reset-username" required />
            </p>
            <p>
              <label for="reset-email">Email</label>
              <input v-model="resetForm.email" type="email" id="reset-email" required />
            </p>
            <p>
              <label for="reset-password">Mật khẩu mới</label>
              <input v-model="resetForm.newPassword" type="password" id="reset-password" required />
            </p>
            <p>
              <label for="reset-confirm">Xác nhận mật khẩu mới</label>
              <input v-model="resetForm.confirmPassword" type="password" id="reset-confirm" required />
            </p>
            <button type="submit">Đặt lại mật khẩu</button>
          </form>
          <div v-if="resetErrorMessage" style="color:red; margin-top:10px;">
            {{ resetErrorMessage }}
          </div>

          <a href="#" class="toggle-link" @click.prevent="showReset = false">
            Quay lại đăng nhập
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
body {
  margin: 0;
  font-family: "Segoe UI", Arial, sans-serif;
  background: #6af98e;
  height: 100vh;
  overflow: hidden;
}
.wrapper {
  height: 100vh;
  display: flex;
  justify-content: center;  /* căn giữa ngang */
  align-items: center;      /* căn giữa dọc */
  background: #6af98e;      /* nền xanh nhạt */
}

.container {
  display: flex;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
  width: 950px;
  padding: 50px;
  animation: fadeIn 1s ease;
  max-height: 90vh;
}
.left {
  flex: 1;
  padding-right: 40px;
}
.logo-title {
  display: flex;
  align-items: center;
  margin-bottom: 45px;
}
.logo-title img {
  width: 90px;
  margin-right: 15px;
}
.logo-title h1 {
  margin: 0;
  font-size: 30px;
  color: #333;
  white-space: nowrap;
}
.left p {
  color: #444;
  font-size: 18px;
  line-height: 1.7;
}
.right {
  flex: 1;
  border-left: 2px solid #eee;
  padding-left: 40px;
}
h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
  font-size: 30px;
}
form {
  display: flex;
  flex-direction: column;
}
form p {
  margin-bottom: 15px;
}
input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 15px;
  box-sizing: border-box;
}
form button {
  width: 100%;
  padding: 12px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 18px;
  font-weight: bold;
}
form button:hover {
  background: #0056b3;
}
.toggle-link {
  display: block;
  text-align: center;
  margin-top: 15px;
  color: #007bff;
  text-decoration: none;
}
.toggle-link:hover {
  color: #1ce029;
}
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
.hidden {
  display: none;
}
</style>
