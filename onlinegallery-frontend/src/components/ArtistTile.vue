<template>
  <v-container>
        <v-card @click="expand" @mouseover="hover = true" @mouseleave="hover=false;" :class="{active:hover}" class="m-0 card">
             <v-img
                 :src="imgUrl"
                 :height="height"
             />
        </v-card>
        <ArtistDialogue :dialog.sync="clicked" @closeDialog="clicked=false"
                       :artid="artid"
                       :imgUrl="imgUrl"
                       :artistname="artistname"
                       :artdesc="artdesc" />
  </v-container>
</template>

<script>
import ArtistDialogue from "@/components/ArtistDialogue";
export default {
  name: 'image-tile',
  components:{ArtistDialogue},
  props: ["artistid","imgUrl","artistname","artdesc","height"],
  data(){
    return{
      hover:false,
      clicked:false,
      thumbHeight:null
    }
  },
  methods:{
    expand(){
      this.clicked=true;
      console.log("clicked")
    }
  }
};
</script>

<style>
.active{
  cursor: pointer;
}

.title{
  font-family: Roboto;
  text-align: center;
}

.card{
  border: 1px solid black !important;
}
</style>