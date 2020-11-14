<template>
    <div class="placeholder">
      <div> artist login</div>
      <ArtistLoginForm/>
    </div>
  </template>

<script>
import ArtistLoginForm from "@/components/ArtistLoginForm";

export default {
  name: 'artist-login',
  components: {ArtistLoginForm},
}
</script>

<style>

.placeholder{
  padding-top: 300px;
  text-align: center;
}

</style>
