<template>
    <div class="about">
    <p class="text-center text-sm-subtitle-1">Payment processed! Thank you for your patronage</p>
          <v-divider class="pb-8"/>
      <p class="text-center text-sm-subtitle-1"> View your purchases <a @click="handleClick">here</a></p>

    </div>
</template>

<script>

export default {
  name: 'payment processed',
  components: {

  },
  methods:{
    handleClick(){
      this.$router.push("/customer-login");
    }
  }
}
</script>

<style>

.about{
  width:65%;
  margin-left: auto;
  margin-right: auto;
  margin-top:200px;
}

</style>
