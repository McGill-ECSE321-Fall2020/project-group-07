<template>
    <v-container>
      <div class="registration-form">
        <RegistrationForm/>
      </div>
    </v-container>
</template>

<script>
import RegistrationForm from "@/components/RegistrationForm";

export default {
  name: 'register',
  components: {RegistrationForm}
}
</script>

<style>

.registration-form{
  margin-top:10%;
}

</style>
