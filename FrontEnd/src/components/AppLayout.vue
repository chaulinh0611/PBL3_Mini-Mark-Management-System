<template>
  <div>
    <div class="header">
      <div class="logo">
        <img
          src="https://cdn.dribbble.com/userupload/28456975/file/original-923692d84cf3a6905b017e91981ed0af.gif"
          alt="Logo"
        />
      </div>
      <div class="hotline">
        <b>Xin chào {{ user.username }} 🎉</b>
      </div>
    </div>

    <div class="nav">
      <router-link to="/">Trang chủ</router-link>
      <router-link to="/about">Giới thiệu</router-link>
      <router-link to="/customers" v-if="user.role === 'admin'">Khách hàng</router-link>
      <router-link to="/cars">Đăng kí xe</router-link>
      <router-link to="/services">Dịch vụ</router-link>
      <router-link to="/contact">Liên hệ</router-link>
      <button @click="logout" class="logout-btn">Đăng xuất</button>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "AppLayout",
  data() {
    return {
      user: {
        username: localStorage.getItem("username") || "Người dùng",
        role: localStorage.getItem("role") || "user",
      },
    };
  },
  methods: {
    logout() {
      localStorage.clear();
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
/* Header */
.header {
  background: #0b6e4f;
  color: white;
  padding: 10px 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header img {
  height: 70px;
}

/* Navigation */
.nav {
  background: #073b3a;
  padding: 10px 40px;
  display: flex;
  gap: 30px;
}
.nav a {
  color: white;
  text-decoration: none;
  font-weight: bold;
}
.nav router-link {
  color: white;
  text-decoration: none;
  font-weight: bold;
}
.nav button {
  color: white;
  text-decoration: none;
  font-weight: bold;
}
.nav a:hover {
  text-decoration: underline;
}
.nav button:hover {
  text-decoration: underline;
}
.logout-btn {
  font-size: 16px;
  background: none;
  border: none;
  color: white;
  font-weight: bold;
  cursor: pointer;
}
.hotline{
    color: white;
    font-size: 20px;
}
</style>
