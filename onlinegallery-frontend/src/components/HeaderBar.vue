<template>
  <v-container class="header">
    <div>
         <v-app-bar style = "justify-content: space-evenly!important" color="white" class="navbar" fixed elevation="0" >
           <v-row style = "justify-content: space-evenly!important">

             <v-col :sm="1" class = "col">
                 <router-link to="/home" class="black-link">Home</router-link>
             </v-col>

             <v-col :sm="1" class="col">
                 <router-link to="/about" class="black-link">About</router-link>
             </v-col>

             <v-col :sm="1" class="col">
                 <router-link to="/discover" class="black-link">Discover</router-link>
             </v-col>

             <v-col :sm="1" class="col">
                 <router-link to="/all-artists" class="black-link">Artists</router-link>
             </v-col>

             <v-col class = "portal" justify="center">
              <v-menu
                v-for="([text, rounded], index) in btns"
                :key="text"
                :rounded="rounded"
                offset-y
              >
              <template v-slot:activator="{ attrs, on }">
                <v-btn
                  :color="colors[index]"
                  class="black--text ma-5"
                  id = "noMargin"
                  v-bind="attrs"
                  v-on="on"
                  style="margin: 0px!important, flex: 0 0 16.6666666667%;"
                >
                  {{ text }}
                </v-btn>
              </template>

              <v-list>
                <v-list-item
                  v-for="item in items"
                  :key="item"
                  router-link :to="{path: '/' + item + '-login'}"
                >
                <v-list-item-title v-text="item"></v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>

          <v-col :sm="1" class="col">
            <router-link to="/register" class="black-link">Register</router-link>
          </v-col>

          <v-col :sm="1" class = "col facebook">
            <v-btn text x-small @click="handleHelp">
              <v-icon>
                mdi-help
              </v-icon>
            </v-btn>
          </v-col>

          <v-col :sm="1" class = "col facebook">
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
    name:"header-bar",
    data: () => ({
      btns: [
        ['Portal', 'b-xl'],
      ],
      colors: ['white accent-4', 'error', 'white darken-1'],
      items: ["Artist", "Customer", "Admin"],
    }),
    methods:{
      handleHelp(){
        this.$router.push("/help")
      }
    }
  }

</script>

<style scoped>
.navbar{
  width: 90%;
  height: 160px !important;
  margin-left: auto;
  margin-right: auto;
  padding-top:50px;
  border:1px solid red;
}


.portal{
  border-right: none!important;
  border-left: none!important;
  border-top: none!important;
}

.facebook{
  border: none!important;
}

.navbar .row{
  justify-content: center;
  align-items: center;
}

.v-btn{
  text-transform: none!important;
  letter-spacing: normal!important;
  font-family: "Roboto", sans-serif!important;
  font-size: 16px!important;
  font-weight: 400!important;
}

.v-btn--contained{
  box-shadow: none!important;
}

.v-btn:not(.v-btn--round).v-size--default{
  padding: 0px!important;
}

.black-link{
  color:black !important;
  text-decoration: none !important;
}

.col{
  text-align: center;
  flex-grow: 0!important;
}

.first-col{
  text-align: right;
  flex-grow: 0!important;
}

#noMargin{
  margin: 0px!important;
  margin-bottom: -10px!important;
  padding-bottom: 10px!important;
}

.navbar .col:hover{
  border-bottom: 2px black solid;
}

.v-btn:before{
  left: 5000%!important;
}

</style>
