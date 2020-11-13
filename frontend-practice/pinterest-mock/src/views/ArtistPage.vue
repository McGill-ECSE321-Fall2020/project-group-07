<template>
    <v-container>
      <div class="artist-dash">
        <v-row>
          <v-col :cols="3">
            <v-card height="320">
              <v-img height="320" src="https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTIwNjA4NjMzNTM4MDUzNjQ0/salvador-dali-40389-2-402.jpg" />
            </v-card>
          </v-col>

          <v-col :cols="9">
            <v-card height="320">
                <div>
                  <v-card-title> {{username}} </v-card-title>
                </div>
                <div class="desc">
                  <perfect-scrollbar>
                    <v-card-text class="text-justify pa-5"> {{desc}} </v-card-text>
                  </perfect-scrollbar>
                </div>
            </v-card>
          </v-col>
        </v-row>

         <v-row>

                    <v-col :cols="3">
                      <v-btn small text  >change profile pic</v-btn>
                    </v-col>

                    <v-col :cols="2">
                      <v-btn small text  @click="editDesc">update bio</v-btn>
                    </v-col>

                    <v-col :cols="3">
                      <v-btn small text  @click="startUpload">upload artworks</v-btn>
                    </v-col>

                    <v-col :cols="2">
                      <v-btn small text  >portfolio</v-btn>
                    </v-col>

                    <v-col :cols="2">
                      <v-btn small text  >view sales</v-btn>
                    </v-col>


          </v-row>


        <v-dialog :value="dialog" width="600" @click:outside="dialog=false">

          <v-card height="610" width="600" class="pa-5">
            <div>
                <v-textarea v-model="desc" no-resize outlined clearable clear-icon="mdi-close-circle"  :value="desc" height="500" class="text-justify"/>
            </div>

            <div class="text-right">
              <v-btn outlined @click="dialog=false">
                done
              </v-btn>
            </div>
          </v-card>

        </v-dialog>

        <v-dialog :value="upload" width="600" @click:outside="upload=false">

          <v-card height="610" width="600" class="pa-5">
              <Uploader/>
          </v-card>

        </v-dialog>

      </div>

<!--      <div>-->
<!--        <Uploader/>-->
<!--      </div>-->
    </v-container>
</template>

<script>
import Vue from 'vue';
import PerfectScrollbar from 'vue2-perfect-scrollbar'
import 'vue2-perfect-scrollbar/dist/vue2-perfect-scrollbar.css'
Vue.use(PerfectScrollbar);

import Uploader from "@/components/Uploader";
export default {
  name: 'artist-page',
  components: {Uploader},
  props:["username"],
  data(){
    return{
      dialog:false,
      upload:false,
      desc:"it amet, consectetur adipiscing elit. Nullam in orci mi. Cras accumsan turpis ut metus placerat, quis lobortis magna vehicula. Sed nisi nulla, pulvinar nec sapien ac, sagittis bibendum quam. Curabitur id ligula quis elit porta pellentesque ac vel libero. Proin finibus nibh vel commodo malesuada. Vestibulum volutpat mauris nisl. Vestibulum luctus lorem at nisl elementum, nec placerat lacus auctor. Integer ac elit a urna ornare sollicitudin in vitae libero. Phasellus ac egestas metus. Integer pellentesque quis ex ac fringilla. Pellentesque placerat sem vitae justo dictum, vitae feugiat tellus congue. Etiam sagittis, ligula pulvinar vulputate tempus, urna tortor rhoncus metus, ut sodales erat erat non ex. Nunc quam ipsum, tempus at sapien ut, bibendum facilisis lectus. Suspendisse potenti. Aliquam cursus dictum fringilla. Ut nibh ligula, placerat sit amet ipsum non, porttitor venenatis velit. Nam ante dui, volutpat sed mauris nec, laoreet suscipit enim. Donec ante tellus, vulputate id suscipit at, pretium vitae est. Nunc congue nulla eget eros ornare semper. Praesent in imperdiet turpis."
    }
  },

  methods:{
    editDesc(){
      this.dialog=true;
    },
    startUpload(){
      this.upload=true;
    }
  }
}
</script>

<style>

.artist-dash{
  width: 75%;
  text-align: center;
  margin:auto;
  margin-top: 200px;
  /*background-color: blue;*/
}

.title{
  height:50px;
}

.ps{
  height:220px;
}

.desc{
  height:200px;
  overflow-y: hidden;
}

</style>
