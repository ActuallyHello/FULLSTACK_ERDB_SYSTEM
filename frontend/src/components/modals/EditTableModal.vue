<template>
    <div class="modal is-active">
        <div 
            @click="close"
            class="modal-background">
        </div>
        <div class="modal-content">
            <div class="box">
                <h2 class="is-size-6 mb-3">Изменение сущности:</h2>
                <EditTableComponent 
                    :tables="tables"
                    :editTableId="editTableId"
                    :editTableMaxId="editTableMaxId"
                    :editTableTitle="editTableTitle"
                    :editTablePKFields="editTablePKFields"
                    :editTableFKFields="editTableFKFields"
                    :editTableAttrFields="editTableAttrFields"
                    @editTable="editTable"
                    @close="close"
                />
            </div>
        </div>
        <button 
            @click="close"
            class="modal-close is-large" 
            aria-label="close">
        </button>
    </div>
</template>

<script>

import EditTableComponent from '@/components/models/EditTableComponent.vue'

export default {
    name: 'EditTableModal',
    props: ['tables', 'editTableId', 'editTableMaxId', 'editTableTitle', 'editTablePKFields', 'editTableFKFields', 'editTableAttrFields'],
    components: {
        EditTableComponent,
    },
    data() {
        return {
        }
    },
    methods: {
        close() {
            this.$emit('close');
        },
        editTable(id, tableObj) {
            this.$emit('editTable', id, tableObj);
        },
    }
}
</script>