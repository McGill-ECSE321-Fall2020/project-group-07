<template>
  <v-container>
    <div>
         <v-app-bar color="white" class="navbar" fixed elevation="0" >
           <v-row>

             <v-col :sm="1" class="leftend">
                 <router-link to="/home" class="black-link">Home</router-link>
             </v-col>

             <v-col :sm="1" class="col">
                 <router-link to="/about" class="black-link">about</router-link>
             </v-col>

             <v-col :sm="2" class="col">
                 <router-link to="/discover" class="black-link">discover artworks</router-link>
             </v-col>

             <v-col :sm="2" class="col">
                 <router-link to="/all-artists" class="black-link">view artists</router-link>
             </v-col>

              <v-col :sm="2" class="col">
                 <router-link to="/customer-login" class="black-link">customer portal</router-link>
             </v-col>

             <v-col :sm="2" class="col">
                 <router-link to="/artist-login" class="black-link">artist portal</router-link>
             </v-col>

             <v-col :sm="1" class="col">
                 <router-link to="/register" class="black-link">register</router-link>
             </v-col>

             <v-col :sm="1" class="rightend">
               <v-btn text x-small href="https://www.facebook.com/Completely-Secure-Gallery-107431924517668" target="_blank">
                 <v-icon color="#3b5998">
                   mdi-facebook
                 </v-icon>
               </v-btn>
             </v-col>


           </v-row>
          </v-app-bar>
      </div>
  </v-container>
</template>

<script>
export default {
  name:"header-bar"
}
</script>

<style>
.navbar{
  width:74%;
  height: 160px !important;
  margin-left: auto;
  margin-right: auto;
  padding-top:50px;
  border:1px solid red
}

.black-link{
  color:black !important;
  text-decoration: none !important;
}

.col{
  text-align: center;
}

.rightend{
  text-align: right;
}

.leftend{
  text-align: left;
}
</style>