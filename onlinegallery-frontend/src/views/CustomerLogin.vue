<template>
    <div class="placeholder">
      <div>Customer Login</div>
      <CustomerLoginFormPortal/>
    </div>
</template>

<script>
import CustomerLoginFormPortal from "@/components/CustomerLoginFormPortal";

export default {
  name: 'customer-login',
   components: {CustomerLoginFormPortal}
}
</script>

<style>

.placeholder{
  padding-top: 300px;
  text-align: center;
}

</style>
