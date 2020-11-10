<template>
  <v-container>
    <div class="navbar">
         <v-app-bar color="white" class="navbar" fixed elevation="0" >
           <v-row>

             <v-col :sm="1" class="col">
                 <router-link to="/" class="black-link">Home</router-link>
             </v-col>

             <v-col :sm="1">
                 <router-link to="/about" class="black-link">about</router-link>
             </v-col>

             <v-col :sm="2">
                 <router-link to="/discover" class="black-link">discover artworks</router-link>
             </v-col>

             <v-col :sm="2">
                 <router-link to="/" class="black-link">view artists</router-link>
             </v-col>

              <v-col :sm="2">
                 <router-link to="/" class="black-link">customer portal</router-link>
             </v-col>

             <v-col :sm="2">
                 <router-link to="/" class="black-link">artist portal</router-link>
             </v-col>

              <v-col :sm="2" class="col">
                <v-text-field
                    label="search"
                solo
                dense
                    rounded
              ></v-text-field>
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
}

.black-link{
  color:black !important;
  text-decoration: none !important;
}

.col{
  text-align: center;
}
</style>