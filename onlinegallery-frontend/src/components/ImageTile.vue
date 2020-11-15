<template>
  <v-container>
        <v-card @click="expand" @mouseover="hover = true" @mouseleave="hover=false;" :class="{active:hover}" class="m-0 card">
             <v-img
                 :src="imgUrl"
                 :height="height"
             />

        </v-card>
        <ImageDialogue :dialog.sync="clicked" @closeDialog="clicked=false"
                       :artid="artid"
                       :imgUrl="imgUrl"
                       :artname="artname"
                       :artistname="artistname"
                       :artdesc="artdesc"
                       :medium="medium"
                       :dimension="dimension"
                       :price="price"
                       @added-to-cart="handleAddedToCart"
        />
  </v-container>
</template>

<script>
import ImageDialogue from "@/components/ImageDialogue";
export default {
  name: 'image-tile',
  components:{ImageDialogue},
  props: ["artid","imgUrl","artname","artistname","artdesc","medium", "dimension", "price","height"],
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
    },
    handleAddedToCart(){
      this.$emit("gatherID", this.artid);
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