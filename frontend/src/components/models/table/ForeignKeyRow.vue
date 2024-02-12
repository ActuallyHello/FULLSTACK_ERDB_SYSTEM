<template>
    <tr>
        <td class="specificator">FK</td>
        <td>
            <div class="field">
                <div class="select is-fullwidth">
                    <select 
                        v-model="selectedValue"
                        @change="changeFKRow">
                        <option
                            v-for="(o, index) of options"
                            :key="index"
                            :value="fkSelectedPower + ':' + o.table"
                            :selected="o.isSelected"
                        >
                            {{ fkSelectedPower }}: {{ o.table }} ({{ o.tablePK }})
                        </option>
                    </select>
                </div>
            </div>
        </td>
        <td class="specificator has-text-centered">
            <button 
                @click="clickOnDeleteFKRow"
                class="delete button is-danger">
            </button>
        </td>
    </tr>
</template>

<script>
export default {
    name: 'ForeignKeyRow',
    props: ['fkId', 'fkSelectedPower', 'options', 'isEdit'],
    data() {
        return {
            id: this.fkId,
            selectedValue: this.isEdit === null ? '' : this.fkSelectedPower + ":" + this.options.filter(o => o.isSelected == true).pop()?.table || 'Ошибка!'
        }
    },
    created() {
        console.log(this.options || 'abc');
    },  
    methods: {
        clickOnDeleteFKRow() {
            this.$emit('clickOnDeleteFKRow', this.id);
        },
        changeFKRow() {
            const [power, selected] = this.selectedValue.split(':');
            this.$emit('changeFKRow', this.id, selected, power);
        }
    }
}
</script>

<style scoped>
    
</style>