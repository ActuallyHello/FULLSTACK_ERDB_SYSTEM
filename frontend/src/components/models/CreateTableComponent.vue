<template>
    <div>
        <table class="table is-bordered is-fullwidth">
            <thead>
                <tr>
                    <td colspan="3">
                        <div class="field">
                            <p class="control is-expanded">
                                <input 
                                    v-model="tableTitle"
                                    class="input" 
                                    type="text" 
                                    placeholder="Название таблицы">
                            </p>
                        </div>
                    </td>
                </tr>
            </thead>
            <tbody>
                <PrimaryKeyRow 
                    v-for="[pkId, pkTitle] in pkFieldsMap" 
                    :key="pkId" 
                    :pkId="pkId" 
                    :pkTitle="pkTitle" 
                    @clickOnDeletePKRow="clickOnDeletePKRow"
                    @changePKRow="changeOnPKRow"
                />
                <ForeignKeyRow 
                    v-for="[fkId, fkSelected] of fkFieldsMap"
                    :key="fkId"
                    :fkId="fkId"
                    :fkSelectedPower="fkSelected.power"
                    :options="collectTablesForOptions(fkSelected.title)"
                    @changeFKRow="changeOnFKRow"
                    @clickOnDeleteFKRow="clickOnDeleteFKRow"
                />
                <AttributeRow 
                    v-for="[attrId, attrTitle] in attrFieldsMap" 
                    :key="attrId" 
                    :attrId="attrId" 
                    :attrTitle="attrTitle" 
                    @clickOnDeleteAttrRow="clickOnDeleteAttrRow"
                    @changeOnAttrRow="changeOnAttrRow"
                />
            </tbody>
        </table>
        <div class="buttons">
            <button class="button mt-2" @click="addRow(pkFieldsMap)">
                <span class="icon">
                    <i><img src="@/assets/key.png"></i>
                </span>
                <span>PK</span>
            </button>
            <div class="control has-icons-left mr-2">
                <div class="select">
                    <select 
                        @change="addRow(fkFieldsMap, {
                            title: '',
                            power: fkSelectedPower
                        })"
                        v-model="fkSelectedPower"
                        :disabled="tables.size == 0"
                    >
                        <option disabled selected value="">FK</option>
                        <option value="1-1">1-1</option>
                        <option value="N-1">N-1</option>
                    </select>
                </div>
                <div class="icon is-small is-left">
                    <i class="p-2"><img src="@/assets/link.png"></i>
                </div>
            </div>
            <button class="button mt-2" @click="addRow(attrFieldsMap)">
                <span class="icon">
                    <i><img src="@/assets/letter-t.png"></i>
                </span>
                <span>ATTR</span>
            </button>
            <button class="button mt-2" @click="saveTable">
                <span class="icon">
                    <i><img src="@/assets/check-mark.png"></i>
                </span>
                <span>Сохранить</span>
            </button>
        </div>
    </div>
</template>

<script>
import { toast } from 'bulma-toast'

import PrimaryKeyRow from '@/components/models/table/PrimaryKeyRow.vue';
import ForeignKeyRow from '@/components/models/table/ForeignKeyRow.vue';
import AttributeRow from '@/components/models/table/AttributeRow.vue';

export default {
    name: 'CreateTableComponent',
    props: ['tables'],
    components: {
        PrimaryKeyRow,
        ForeignKeyRow,
        AttributeRow
    },
    data() {
        return {
            id: 1,

            tableTitle: '',
            fkSelectedPower: '',

            pkFieldsMap: new Map([[0, '']]),
            fkFieldsMap: new Map(),
            attrFieldsMap: new Map(),
        }
    },
    methods: {
        addRow(fieldsMap, defaultValue='') {
            fieldsMap.set(this.id, defaultValue);
            this.id++;
        },

        clickOnDeletePKRow(id) {
            this.pkFieldsMap.delete(id);
        },
        changeOnPKRow(id, newValue) {
            this.pkFieldsMap.set(id, newValue);
        },

        clickOnDeleteFKRow(id) {
            this.fkFieldsMap.delete(id);
        },
        changeOnFKRow(id, selected, power) {
            this.fkFieldsMap.set(id, {
                title: selected,
                power: power
            });
        },

        clickOnDeleteAttrRow(id) {
            this.attrFieldsMap.delete(id);
        },
        changeOnAttrRow(id, newValue) {
            this.attrFieldsMap.set(id, newValue);
        },

        collectTablesForOptions(fkSelectedTitle) {
            let tableOptions = [];
            for (let [key, value] of this.tables) {
                tableOptions.push({
                    isSelected: fkSelectedTitle != '' ? fkSelectedTitle == value.tableTitle : false,
                    table: value.tableTitle,
                    tablePK: value.pkFieldsMap.values().next().value, // первый элемент
                })
            }
            return tableOptions;
        }, 

        isAnyNonEmptyFields(valueArray) {
            for (let value of valueArray) {
                if (value == '') {
                    return true;
                }
            }
            return false;
        },

        saveTable() {
            const fkValues = [];
            this.fkFieldsMap.forEach((val, key) => fkValues.push(val.title));
            if (this.pkFieldsMap.size == 0) {
                this.showDangerMessage("Необходимо добавить первичный ключ!");
                return;
            } else if (this.isAnyNonEmptyFields(this.pkFieldsMap.values()) || this.isAnyNonEmptyFields(this.attrFieldsMap.values()) ||
                    this.isAnyNonEmptyFields(fkValues)) {
                this.showDangerMessage("Невозможно добавить пустое поле!");
                return;
            }
            this.$emit('saveTable', {
                tableTitle: this.tableTitle,
                pkFieldsMap: this.pkFieldsMap,
                fkFieldsMap: this.fkFieldsMap,
                attrFieldsMap: this.attrFieldsMap,
            });
            this.$emit('close');
        },

        showDangerMessage(message) {
            toast({
                message: message,
                type: 'is-danger',
                dismissible: true,
                pauseOnHover: true,
                duration: 2500,
                position: 'bottom-right',
            });
        }
    },
}
</script>

<style>
.specificator {
    width: 50px;
    vertical-align: middle !important;
}
</style>