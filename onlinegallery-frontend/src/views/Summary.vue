<template>
   <v-card class = "fit" dark>
      <v-card-title>
         <v-text-field
         v-model="search"
         append-icon="mdi-magnify"
         label="Search"
         single-line
         hide-details
         ></v-text-field>
      </v-card-title>
      <v-data-table
         :headers="headers"
         :items="info"
         :search="search"
      ></v-data-table>
   </v-card>
   <!--
   <v-data-table
    item-key="name"
    class="elevation-1"
    loading
    loading-text="Loading... Please wait"
   ></v-data-table>
   -->
</template>

<script>
import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
Vue.use(VueAxios,axios);

export default {
   name: 'gallery-summary',
   data () {
      return {
         search: '',
         headers: [
            {
            text: 'Customer',
            align: 'start',
            value: 'name',
            },
            { text: 'Price', value: 'price' },
            { text: 'Commission', value: 'commission' },
            { text: 'Date', value: 'date' },
            { text: 'Artist', value: 'artist' },
            { text: 'Artwork', value: 'artwork' },
            { text: 'Shipment', value: 'shipment' },
         ],
         info: [
            
         ],
      }
   },
   methods:{},
   mounted(){
      axios.get(`https://onlinegallery-backend-g7.herokuapp.com/generateSummary`)
      .then(res=>{
         let datas = res.data;
         for(let i = 0; i<datas.length; i++){
            let data = datas[i];

            let info ={
               name: data.customerName,
               price: data.price,
               commission: data.comission,
               date: data.datePurchased,
               artist: data.artistName,
               artwork: data.name,
               shipment: data.shipmentType
            }
            this.info.push(info);
         }
      })
   }
}
</script>

<style>

.fit{
   transform: scale(0.8);
   margin-top: 150px;
}

</style>
