<template>
    <div class="placeholder">
      <div>Admin Login</div>
      <AdminLoginForm/>
    </div>
  </template>

<script>
import AdminLoginForm from "@/components/AdminLoginForm";

export default {
  name: 'admin-login',
  components: {AdminLoginForm}
}
</script>

<style>

.placeholder{
  padding-top: 30vh!important;
  text-align: center;
}

</style>