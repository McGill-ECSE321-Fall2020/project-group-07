<template>
  <v-container>
    <v-card class="mx-auto" flat height="20">
      <v-form class="pa-4 pt-6">
        <v-text-field v-model="username" label="Username" required />

        <v-text-field
          v-model="password"
          :type="'password'"
          name="input-10-1"
          label="Password"
        />
      </v-form>
      <div class="text-center">
        <v-btn class="mr-4" @click="Login" outlined small> Login </v-btn>
      </div>
      <v-card-text class="text-center">{{ responseMsg }}</v-card-text>
    </v-card>
  </v-container>
</template>


<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);
export default {
  name: "customer-login",

  data: () => ({
    username: "",
    customerId:"",
    password: "",
    responseMsg: "",
  }),

  methods: {
    Login() {

      axios.get(`https://onlinegallery-backend-g7.herokuapp.com/getCustomerByUsername/${this.username}`)
        .then((res) => {
          this.responseMsg = this.username + " logged in!";
          this.customerId=res.data.customerId;
          this.$emit("customer-loggedin",this.username, this.customerId);
        })
        .catch((error) => {
          this.responseMsg = error.response.data;
          this.$emit("customer-not-loggedin");
        });
      this.responseMsg="";
      },
  },
};
</script>

<style scoped>
.active {
  cursor: pointer;
}
.title {
  font-family: Roboto;
  text-align: center;
}
.card {
  border: 1px solid black !important;
}
</style>