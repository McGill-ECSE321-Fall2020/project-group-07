<template>
    <div class="placeholder">
      <div> Customer Login</div>
      <CustomerLoginForm/>
    </div>
</template>

<script>
import CustomerLoginForm from "@/components/CustomerLoginForm";

export default {
  name: 'customer-login',
  components: {CustomerLoginForm}
}
</script>

<style>

.placeholder{
  padding-top: 300px;
  text-align: center;
}

</style>
