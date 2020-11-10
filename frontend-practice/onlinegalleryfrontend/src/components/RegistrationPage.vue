<template>
    <div id="registration">
        <h1>Registration</h1>
        <input type="text" name="username" v-model="input.username" placeholder="Username" />
        <input type="text" name="firstName" v-model="input.firstName" placeholder="First Name" />
        <input type="text" name="lastName" v-model="input.lastName" placeholder="Last Name" />
        <input type="text" name="email" v-model="input.email" placeholder="Email" />
        <input type="password" name="password" v-model="input.password" placeholder="Password" />
        <button type="button" v-on:click="login()">Register</button>
    </div>
</template>

<script>
    export default {
        name: 'Registration',
        data() {
            return {
                input: {
                    username: "",
                    firstName: "",
                    lastName: "",
                    email: "",
                    password: ""
                }
            }
        },
        methods: {
            registration() {
                if(this.input.username != "" && this.input.password != ""
                        && this.input.firstName!= "" && this.input.lastName != "" 
                                    && this.input.email!= "") {
                    console.log("Wow");
                } else {
                    console.log("Cannot have null fields!");
                }
            }
        }
    }
</script>

<style scoped>
    #registration {
        width: 500px;
        border: 1px solid #CCCCCC;
        background-color: #FFFFFF;
        margin: auto;
        margin-top: 200px;
        padding: 20px;
    }
</style>