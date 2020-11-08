<template >
  <v-app>
  <v-container class="gray darken-4" fluid>
    <ImageFrame :imgSrc="require('@/assets/sunflower.jpg')" />
    <ImageFrame imgSrc="https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Vincent_van_Gogh_-_Wheatfield_with_crows_-_Google_Art_Project.jpg/1920px-Vincent_van_Gogh_-_Wheatfield_with_crows_-_Google_Art_Project.jpg" />
    <ImageFrame :imgSrc="require('@/assets/sf2.jpg')" />


  </v-container>
  </v-app>
</template>

<script>
import ImageFrame from "@/components/ImageFrame";
export default {
  name: 'App',
  components: {ImageFrame},
  data: () => ({
  }),
};
</script>

<style>
</style>
