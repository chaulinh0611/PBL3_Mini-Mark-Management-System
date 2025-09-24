
<script>
import axios from "axios";

export default {
  name: "CustomerList",
  data() {
  return {
    customers: [],
    showDeleteModal: false,  // trạng thái hiển thị modal
    selectedCustomerId: null, // id khách hàng muốn xóa
  };
},

  mounted() {
    this.fetchCustomers();
  },
 methods: {
  async fetchCustomers() {
    try {
      const response = await axios.get("http://localhost:8083/api/user/getAllUsers");
      this.customers = response.data;
    } catch (error) {
      console.error("Lỗi khi lấy danh sách khách hàng:", error);
    }
  },
  // Mở modal
  openDeleteModal(id) {
    this.selectedCustomerId = id;
    this.showDeleteModal = true;
  },
  // Xác nhận xóa
  async confirmDeleteCustomer() {
    try {
      await axios.delete(`http://localhost:8083/api/user/${this.selectedCustomerId}`);
      this.showDeleteModal = false;
      this.fetchCustomers();
    } catch (error) {
      console.error("Lỗi khi xóa khách hàng:", error);
    }
  },
  // Hủy xóa
  cancelDelete() {
    this.showDeleteModal = false;
    this.selectedCustomerId = null;
  }
},

};
</script>

<template>
  <div class="container">
    <div class="content">
      <h1>Danh sách khách hàng</h1>
      <div class="btn-container">
        <router-link to="/customers/add" class="btn">+ Thêm khách hàng</router-link>
      </div>
      <table>
        <thead>
          <tr>
            <th>STT</th>
            <th>Tên đăng nhập</th>
            <th>Email</th>
            <th>Vai trò</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(c, index) in customers.filter(c => c.role === 'User')" :key="c.id">
            <td class="id">{{ index + 1 }}</td> <!-- đây là STT -->
            <td>{{ c.username }}</td>
            <td>{{ c.email }}</td>
            <td>{{ c.role }}</td>
            <td>
              <router-link :to="`/customers/edit/${c.id}`" class="btn-edit">Sửa</router-link>
              <button @click="openDeleteModal(c.id)" class="btn-delete">Xóa</button>
            </td>
          </tr>
        </tbody>

      </table>
    </div>
  </div>
    <!-- Modal Xóa -->
  <div v-if="showDeleteModal" class="modal-overlay">
    <div class="modal">
      <h3>Bạn có chắc chắn muốn xóa người dùng này không?</h3>
      <div class="modal-buttons">
        <button @click="confirmDeleteCustomer" class="btn btn-delete">Xóa</button>
        <button @click="cancelDelete" class="btn btn-edit">Hủy</button>
      </div>
    </div>
  </div>

</template>

<style scoped>
body{
  font-family: "Segoe UI", Arial, sans-serif;
  background: #f5f5f5;
}
.container{
  padding: 50px;
}
.content{
  margin: 0;
}
.btn-container{
  display: flex;
  justify-content:flex-end;
  padding: 15px;
  font-size: 18px;
}
.btn{
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
  font-size: 18px;
}

th {
  background: #f4f4f4;
}
.btn-edit, .btn-delete {
  margin-right: 10px;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}
.btn-delete{
  font-size: 16px;
}
.btn { background: #0b6e4f; color: white; text-decoration: none; }
.btn-edit { background: #ffa500; color: white; text-decoration: none;}
.btn-delete { background: #d9534f; color: white; border: none; }

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal {
  background: white;
  padding: 30px;
  border-radius: 8px;
  width: 400px;
  text-align: center;
}

.modal-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
}

</style>
