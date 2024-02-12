<template>
    <div>
        <div class="box">
            <h2 class="is-size-5 mb-4">Описание модели:</h2>
            <div class="columns">
                <div class="column">
                    <div class="field">
                        <div class="label">Название модели:</div>
                        <div class="control">
                            <input type="text" class="input" v-model="modelTitle">
                        </div>
                        <p class="help"></p>
                    </div>
                    <div class="field">
                        <div class="label">Бизнес-процесс модели:</div>
                        <div class="control">
                            <input type="text" class="input" v-model="modelTopic">
                        </div>
                        <p class="help"></p>
                    </div>
                </div>
                <div class="column">
                    <div class="field">
                        <div class="label">Описание модели:</div>
                        <div class="control">
                            <textarea class="textarea" placeholder="" v-model="modelDescription"></textarea>
                        </div>
                        <p class="help"></p>
                    </div>
                </div>
            </div>
        </div>

        <div class="box">
            <h2 class="is-size-5 mb-4">Сущности модели:</h2>
            <button @click="isCreateTableModalOpen = true" class="button is-primary">
                Добавить сущность
            </button>
            <CreateTableModal 
                v-if="isCreateTableModalOpen" 
                @close="isCreateTableModalOpen = false" 
                :tables="tables"
                @saveTable="saveTable"
            />
            <EditTableModal 
                v-if="isEditTableModalOpen" 
                @close="isEditTableModalOpen = false" 
                :tables="tables"
                :editTableId="editTableId"
                :editTableMaxId="editTableMaxId"
                :editTableTitle="editTableTitle"
                :editTablePKFields="editTablePKFields"
                :editTableFKFields="editTableFKFields"
                :editTableAttrFields="editTableAttrFields"
                @editTable="editTable"
            />
            <div class="mt-5">
                <div class="columns is-multiline">
                    <TablePreviewComponent 
                        v-for="[tableId, table] in tables"
                        :key="tableId"
                        :tableId="tableId"
                        :table="table"
                        @clickOnDeletePreviewTable="clickOnDeletePreviewTable"
                        @clickOnEditPreviewTable="clickOnEditPreviewTable"
                    />
                </div>
            </div>
            <hr>
            <button 
                class="button is-primary"
                @click="saveModel"    
            >Создать модель</button>
        </div>
    </div>
</template>
  
<script>

import CreateTableModal from '@/components/modals/CreateTableModal.vue'
import EditTableModal from '@/components/modals/EditTableModal.vue'
import TablePreviewComponent from '@/components/models/TablePreviewComponent.vue'
import axios from 'axios';
import { toast } from 'bulma-toast'

export default {
    name: 'CreateModelView',
    components: {
        CreateTableModal,
        EditTableModal,
        TablePreviewComponent,
    },
    data() {
        return {
            isCreateTableModalOpen: false,
            isEditTableModalOpen: false,

            modelTitle: '',
            modelDescription: '',
            modelTopic: '',

            personId: 1,

            id: 0,
            tables: new Map(),
            relations: [],

            editTableMaxId: -1,
            editTableTitle: null,
            editTablePKFields: null,
            editTableFKFields: null,
            editTableAttrFields: null,
        }
    },
    methods: {
        saveTable(tableObj) {
            this.tables.set(this.id, tableObj);
            this.id++;
        },

        editTable(id, tableObj) {
            this.tables.set(id, tableObj);
        },

        clickOnDeletePreviewTable(id) {
            this.tables.delete(id);
        },

        clickOnEditPreviewTable(id, table) {
            this.editTableId = id;
            this.editTableTitle = table.tableTitle;
            this.editTablePKFields = table.pkFieldsMap;
            this.editTableFKFields = table.fkFieldsMap;
            this.editTableAttrFields = table.attrFieldsMap;
            
            let max = -1;
            for (let id of this.editTablePKFields.keys()) {
                if (max < id) {
                    max = id;
                }
            }
            for (let id of this.editTableFKFields.keys()) {
                if (max < id) {
                    max = id;
                }
            }
            for (let id of this.editTableAttrFields.keys()) {
                if (max < id) {
                    max = id;
                }
            }
            this.editTableMaxId = max;

            this.isEditTableModalOpen = true;
        },

        async saveModel() {
            if (!this.checkValidInputs()) {
                return;
            }
            let data = this.mapModelToPostData();
            console.log("TRY TO SEND -", data);
            let modelId = null;
            await axios
                .post(`/models`, data)
                .then(response => {
                    console.log("YES", response);
                    toast({
                        message: "Модель создана!",
                        type: 'is-primary',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2500,
                        position: 'bottom-right',
                    })
                    this.$router.push('/');
                })
                .catch(error => {
                    console.log("ERROR", error);
                    toast({
                        message: "Ошибка при создании модели! Обратитесь к адиминстратору!",
                        type: 'is-danger',
                        dismissible: true,
                        pauseOnHover: true,
                        duration: 2500,
                        position: 'bottom-right',
                    })
                });

            if (modelId) {
                if (this.$route.task_id) {
                    axios
                        .post(`tasks/send-result/`, {
                            model_id: modelId,
                            task_id: this.$route.query.task_id
                        })
                        .then(response => {
                            console.log(response, response.data, "WE DID IT")
                        })
                        .catch(error => {
                            console.log(error, error.response, "WE DIDNT DINT")
                        })
                }
            }
        },
        
        mapModelToPostData() {
            let data = {
                'title': this.modelTitle,
                'description': this.modelDescription,
                'topic': this.modelTopic,
                'personId': this.personId,
            };
            let formTables = [];
            let formRelations = [];
            let fkBuffer;
            for (let table of this.tables.values()) {
                fkBuffer = Array.from(table.fkFieldsMap.values());
                formTables.push(
                    {
                        'title': table.tableTitle,
                        'pkFields': Array.from(table.pkFieldsMap.values()),
                        'attrFields': Array.from(table.attrFieldsMap.values()),
                        'fkFields': fkBuffer.map(item => item.title),
                    }
                );
                for (let relation of fkBuffer) {
                    formRelations.push(
                        {
                            'fromEntity': table.tableTitle,
                            'power': relation.power,
                            'toEntity': relation.title,
                        }
                    )
                }
            }
            data.tableList = formTables;
            data.relationList = formRelations;
            return data;
        },

        checkValidInputs() {
            if (this.modelTitle == "" || this.modelDescription == "" || this.modelTopic == "") {
                toast({
                    message: "Невозможно создать модель! Опишите модель! ",
                    type: 'is-danger',
                    dismissible: true,
                    pauseOnHover: true,
                    duration: 2500,
                    position: 'bottom-right',
                })
                return false;
            }
            if (this.tables.size == 0) {
                toast({
                    message: "Невозможно создать модель без сущностей!",
                    type: 'is-danger',
                    dismissible: true,
                    pauseOnHover: true,
                    duration: 3500,
                    position: 'bottom-right',
                })
                return false;
            }
            return true;
        }
    }
}
</script>

<style scoped>
div#main {
    background-color: rgb(240, 239, 239);
    height: 100vh;
}
</style>
  