<template>
  <v-container fluid>
      <div>
      <HeaderBar/>
          <div class="masonry-container">

            <div class="refresh">
              <v-btn text>
                <v-icon class="black-link">
                  mdi-refresh
                </v-icon>
              </v-btn>
            </div>

            <v-row dense no-gutters v-masonry origin-left="true" horizontal-order="true" item-selector=".item">
              <v-col :sm="totalWidth" v-for="each in artworks" v-masonry-tile class="item" :key="each.id" >
               <ImageTile :artid="each.id" :imgUrl="each.imgUrl" :artname="each.name" :artistname="each.username" :artdesc="each.description" :medium="each.medium" :dimension="each.dimension" :price="each.price" :height="each.height"/>
              </v-col>
            </v-row>
          </div>
       </div>
  </v-container>
</template>

<script>

export default {
  name: 'artist-page',
  components: {

  }
}
</script>

<style scoped>
.masonry-container {
  width: 75%;
  margin:auto;
  margin-top: 100px;
}

.refresh{
  text-align: center;
  margin-bottom: 50px;
}

.black-link{
  color:black !important;
  text-decoration: none !important;
}
</style>
