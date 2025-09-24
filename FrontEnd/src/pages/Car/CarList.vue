<script>
import axios from "axios";

export default {
  name: "CarList",
  data() {
    return {
      cars: [],
      loading: true,
      error: null,
      user: {
        id: localStorage.getItem("userId"),
        username: localStorage.getItem("username"),
      },
    };
  },
  mounted() {
    this.fetchCars();
  },
  methods: {
    async fetchCars() {
      try {
        console.log("User ID:", this.user.id);
        // gọi API lấy xe theo userId
        const response = await axios.get(
          `http://localhost:8083/api/car/byUser/${this.user.id}`
        );
        console.log("Cars fetched:", response.data);
        this.cars = response.data;
      } catch (err) {
        this.error = "❌ Lỗi khi tải danh sách xe!";
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<template>
  <div class="container">
    <h2>🚗 Danh sách xe đã đăng ký của {{ user.username }}</h2> 
    <router-link to="/cars/add" class="addCar">➕ Đăng ký xe mới</router-link>

    <div v-if="loading">⏳ Đang tải dữ liệu...</div>
    <div v-if="error" class="error">{{ error }}</div>

    <table v-if="!loading && cars.length > 0" class="car-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Biển số</th>
          <th>Ngày đăng ký</th>
          <th>Lần vào gần nhất</th>
          <th>Lần ra gần nhất</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="car in cars" :key="car.id">
          <td>{{ car.id }}</td>
          <td>{{ car.licensePlate }}</td>
          <td>{{ car.registrationDate }}</td>
          <td>{{ car.lastInTime || "Chưa có" }}</td>
          <td>{{ car.lastOutTime || "Chưa có" }}</td>
        </tr>
      </tbody>
      
    </table>

    <p v-else-if="!loading && cars.length === 0">🙅 Bạn chưa đăng ký xe nào.</p>
  </div>
</template>

<style scoped>
.container {
  padding: 20px 40px;
  font-family: "Segoe UI", Arial, sans-serif;
}
h2 {
  margin-bottom: 20px;
  color: #0b6e4f;
}
.car-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
}
.car-table th,
.car-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}
.car-table th {
  background: #f4f4f4;
}
.error {
  color: red;
  margin-top: 10px;
}
.addCar {
  display: inline-block;
  margin-top: 15px;
  padding: 8px 12px;
  background: #0b6e4f;
  color: white;
  text-decoration: none;
  border-radius: 5px;
}
</style>
