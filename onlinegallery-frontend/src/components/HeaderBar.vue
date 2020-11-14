<template>
  <v-container>
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
            <router-link to="/register" class="black-link">register</router-link>
          </v-col>

          <v-col :sm="1" class = "col facebook">
            <v-btn text x-small>
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
  }
</script>

<style>

.navbar{
  width:90%;
  height: 160px !important;
  margin-left: 5%;
  margin-right: 5%;
  padding-top: 50px;
}

.portal, .facebook{
  border: none!important;
}

.row{
  justify-content: center;
  align-items: center;
}

.black-link{
  color:black !important;
  text-decoration: none !important;
}

.col{
  text-align: center;
  flex-grow: 0!important;
}

.navbar .col:hover{
  border-bottom: 2px black solid;
}

</style>