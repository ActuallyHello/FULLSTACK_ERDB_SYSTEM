<template>
    <div class="modal is-active">
        <div 
            @click="close"
            class="modal-background">
        </div>
        <div class="modal-content">
            <div class="box">
                <h2 class="is-size-6 mb-3">Новая сущность:</h2>
                <CreateTableComponent 
                    :tables="tables"
                    @saveTable="saveTable"
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

import CreateTableComponent from '@/components/models/CreateTableComponent.vue'

export default {
    name: 'CreateTableModal',
    props: ['tables'],
    components: {
        CreateTableComponent,
    },
    data() {
        return {
        }
    },
    methods: {
        close() {
            this.$emit('close');
        },
        saveTable(tableObj) {
            this.$emit('saveTable', tableObj);
        },
    }
}
</script>