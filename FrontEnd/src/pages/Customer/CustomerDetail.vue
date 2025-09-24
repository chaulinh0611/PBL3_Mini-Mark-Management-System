<script>
import axios from "axios";

export default {
  name: "CustomerForm",
  computed: {
  roleValue: {
    get() { return this.customer.role || 'user'; },
    set(val) { this.customer.role = val; }
  }
},

  data() {
    return {
      customer: {
        username: "",
        email: "",
        role: "user",
        password: "",
      },
      isEdit: false,
    };
  },
  mounted() {
    if (this.$route.params.id) {
      this.isEdit = true;
      this.fetchCustomer(this.$route.params.id);
    }
  },
  methods: {
    async fetchCustomer(id) {
      try {
        const response = await axios.get(`http://localhost:8083/api/user/${id}`);
        this.customer.username = response.data.username || "";
        this.customer.email = response.data.email || "";
        this.customer.role = response.data.role || "user"; // nếu API null thì giữ User
        this.customer.password = ""; // không load mật khẩu
      } catch (error) {
        console.error("Lỗi khi tải khách hàng:", error);
      }
    },

    async saveCustomer() {
      try {
        if (this.isEdit) {
          await axios.put(
            `http://localhost:8083/api/user/${this.$route.params.id}`,
            this.customer
          );
          // alert("Cập nhật thành công!");
        } else {
          await axios.post("http://localhost:8083/api/user/create", this.customer);
          alert("Thêm mới thành công!");
        }
        this.$router.push("/customers");
      } catch (error) {
        console.error("Lỗi khi lưu:", error);
      }
    },
  },
};
</script>
<template>
  <div class="container">
    <div class="content-card">
      <h1>{{ isEdit ? "Sửa thông tin khách hàng" : "Thêm khách hàng" }}</h1>
      <form @submit.prevent="saveCustomer" class="customer-form">
        <div class="form-group">
          <label>Tên đăng nhập:</label>
          <input v-model="customer.username" placeholder="Nhập tên đăng nhập" required />
        </div>

        <div class="form-group">
          <label>Email:</label>
          <input type="email" v-model="customer.email" placeholder="Nhập email" required />
        </div>
      <div class="form-group">
        <label>Vai trò:</label>
        <input type="text" v-model="customer.role" placeholder="Nhập vai trò"></input>
      </div>




        <div class="form-actions">
          <button type="submit" class="btn">{{ isEdit ? "Cập nhật" : "Thêm mới" }}</button>
          <button type="button" @click="$router.push('/customers')" class="btn btn-back">Quay lại</button>
        </div>
      </form>
    </div>
  </div>
</template>


<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 100px 20px;
  max-height: 100vh;
  /* background: #f5f5f5; */
  margin: 0;
  overflow-y: hidden;
}

.content-card {
  background: #fff;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 500px;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

.customer-form .form-group {
  margin-bottom: 20px;
}

.customer-form label {
  display: block;
  font-weight: 600;
  margin-bottom: 8px;
  color: #555;
  font-size: 18px;
}

.customer-form input,
.customer-form select {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 16px;
  transition: border 0.3s;
}

.customer-form input:focus,
.customer-form select:focus {
  border-color: #0b6e4f;
  outline: none;
}

.form-actions {
  text-align: center;
  margin-top: 25px;
}

.btn {
  background: #0b6e4f;
  color: white;
  padding: 12px 25px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.btn:hover {
  background: #09543a;
}
.btn-back {
  background: #6c757d; /* màu xám nhạt */
  margin-left: 10px;
}

.btn-back:hover {
  background: #5a6268;
}

</style>
