<script>
import router from "@/router";
import axios from "axios";

export default {
  name: "CarRegister",
  data() {
  return {
    car: {
      licensePlate: "",
      registrationDate: new Date().toISOString().split("T")[0], 
      lastInTime: "",
      lastOutTime: "",
    },
    message: "",
  };
},

  methods: {
    async registerCar() {
  try {
    const userId = localStorage.getItem("userId");
    if (!userId || userId === "null") {
      this.message = "❌ Không tìm thấy userId, vui lòng đăng nhập lại.";
      return;
    }

    const response = await axios.post(
      `http://localhost:8083/api/user/${userId}/car`,
      this.car
    );

    this.message = "🚘 Đăng ký xe thành công!";
    console.log("Car registered:", response.data);
    router.push("/cars");
    // reset form
    this.car = {
      licensePlate: "",
      registrationDate: new Date().toISOString().split("T")[0],
      lastInTime: "",
      lastOutTime: "",
    };
  } catch (error) {
    this.message = "❌ Đăng ký thất bại!";
    console.error("Error:", error);
  }
},

  },
};
</script>
<template>
  <div class="car-register">
    <h2>🚗 Đăng ký xe</h2>

    <form @submit.prevent="registerCar">
      <!-- Biển số -->
      <div class="form-group">
        <label>Biển số xe</label>
        <input type="text" v-model="car.licensePlate" required placeholder="Nhập biển số xe" />
      </div>

      <!-- Ngày đăng ký -->
      <div class="form-group">
        <label>Ngày đăng ký</label>
        <input type="date" v-model="car.registrationDate" required />
      </div>


      <button type="submit" class="btn-submit">Đăng ký</button>
         <router-link to="/cars" class="backLink">← Quay lại</router-link>
    </form>

    <div v-if="message" class="message">{{ message }}</div>
  </div>
</template>


<style scoped>
.car-register {
  max-width: 500px;
  margin: 40px auto;
  background: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}
.car-register h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #0b6e4f;
}
.form-group {
  margin-bottom: 15px;
}
.form-group label {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}
.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 6px;
}
.btn-submit {
  background: #0b6e4f;
  color: white;
  font-weight: bold;
  padding: 10px 15px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  width: 100%;
}
.btn-submit:hover {
  background: #073b3a;
}
.message {
  margin-top: 15px;
  text-align: center;
  font-weight: bold;
}
.backLink {
  display: inline-block;
  margin-top: 15px;
  text-decoration: none;
  color: #0b6e4f;
}
</style>
