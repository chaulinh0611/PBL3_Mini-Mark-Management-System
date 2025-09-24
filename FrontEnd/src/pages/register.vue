<script>
export default {
  name: "RegisterPage",
  data() {
    return {
      username: "",
      email: "",
      password1: "",
      password2: "",
      errorMessage: "",
    };
  },
  methods: {
    async register() {
      if (this.password1 !== this.password2) {
        this.errorMessage = "Mật khẩu xác nhận không khớp!";
        return;
      }

      try {
        const res = await fetch("http://localhost:8083/api/user/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
            username: this.username,
            email: this.email,
            password: this.password1,
            role: "User"
            }),
        });

        const data = await res.json();
        console.log("Status: ", res.status);
        console.log("Response: ", data);

        if (!res.ok) {
            // Nếu backend trả về { field: "message" } hoặc { error: "message" }
            this.errorMessage = Object.values(data).join(", ");
            return;
        }

        // alert("Đăng ký thành công!");
        this.$router.push("/login");
        } catch (err) {
        this.errorMessage = err.message;
        }

    },
  },
};
</script>

<template>
  <div class="wrapper">
    <div class="register-box">
      <h2>Đăng ký</h2>
      <form @submit.prevent="register">
        <!-- Username -->
        <label for="username">Tên đăng nhập:</label>
        <input v-model="username" type="text" id="username" required />

        <!-- Email -->
        <label for="email">Email:</label>
        <input v-model="email" type="email" id="email" required />

        <!-- Password1 -->
        <label for="password1">Mật khẩu:</label>
        <input v-model="password1" type="password" id="password1" required />

        <!-- Password2 -->
        <label for="password2">Xác nhận mật khẩu:</label>
        <input v-model="password2" type="password" id="password2" required />

        <!-- Errors -->
        <div v-if="errorMessage" style="color:red; margin-top:8px;">
          {{ errorMessage }}
        </div>

        <button type="submit">Đăng ký</button>
      </form>

      <router-link to="/" class="login-link">
        Bạn đã có tài khoản? Đăng nhập
      </router-link>
    </div>
  </div>
</template>


<style scoped>
.wrapper {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #6af98e;
  overflow: hidden;
}

.register-box {
  background: #fff;
  padding: 40px 50px;
  border-radius: 16px;
  box-shadow: 0 8px 20px rgba(0,0,0,0.2);
  width: 420px;
  animation: fadeIn 1s ease;
  max-height: 95vh;
}

h2 {
  text-align: center;
  margin-bottom: 15px;
  color: #333;
  font-size: 28px;
}

form {
  display: flex;
  flex-direction: column;
}

form label {
  font-size: 15px;
  font-weight: 500;
  margin-bottom: 5px;
}

form input[type="text"],
form input[type="password"],
form input[type="email"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 15px;
  box-sizing: border-box;
  margin-bottom: 12px;
}

form button {
  margin-top: 10px;
  padding: 12px;
  font-size: 18px;
  font-weight: bold;
  color: #fff;
  background: #21b02a;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

form button:hover {
  background: #176d83;
  transform: translateY(-2px);
}

.login-link {
  display: block;
  text-align: center;
  margin-top: 15px;
  color: #007bff;
  text-decoration: none;
  font-size: 15px;
}

.login-link:hover {
  color: #1ce029;
}

@keyframes fadeIn {
  from {opacity: 0; transform: translateY(30px);}
  to {opacity: 1; transform: translateY(0);}
}
</style>
