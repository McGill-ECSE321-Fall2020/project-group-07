<template>
    <div class="placeholder">
      <div>Artist Login</div>
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
  padding-top: 25vw;
  text-align: center;
}

</style>
