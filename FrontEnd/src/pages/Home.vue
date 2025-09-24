<script>
export default {
  name: "Home",
  data() {
    return {
      user: {
        username: localStorage.getItem("username") || "Người dùng",
        role: localStorage.getItem("role") || "user",
      },
    };
  },
};
</script>
<template>
  <div class="container">
    <div class="content">
      <h1>Hệ thống bãi đỗ xe thông minh</h1>
      <p>
        Xin chào <b>{{ user.username }}</b> 🎉<br />
        Bạn đã đăng nhập thành công vào hệ thống quản lý bãi đỗ xe thông minh.<br /><br />
        ✅ Theo dõi tình trạng bãi đỗ xe theo thời gian thực.<br />
        ✅ Đặt chỗ trước để đảm bảo có chỗ đỗ.<br />
        ✅ Thanh toán nhanh chóng, an toàn.<br />
        ✅ Báo cáo chi tiết cho người quản lý.
      </p>
      <img
        src="https://baigiuxethongminh.vn/wp-content/uploads/2020/03/bai-giu-xe-thong-minh-vietparking.jpg"
        alt="Minh họa bãi đỗ xe thông minh"
      />
    </div>
  </div>
</template>




<style scoped>
body {
  margin: 0;
  font-family: "Segoe UI", Arial, sans-serif;
  background: #f5f5f5;
}

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

/* Layout */
.container {
  display: flex;
  max-width: 1200px;
  margin: 20px auto;
  gap: 20px;
}

.content {
  flex: 3;
  background: #fff;
  border-radius: 8px;
  padding: 30px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.content h1 {
  font-size: 26px;
  color: #333;
  margin-top: 0;
}
.content p {
  line-height: 1.7;
  font-size: 16px;
  color: #444;
}
.content img {
  width: 100%;
  margin: 20px 0;
  border-radius: 8px;
}
</style>
