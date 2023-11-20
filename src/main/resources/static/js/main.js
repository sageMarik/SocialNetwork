var messageApi = Vue.resource('/message{/id}')

Vue.component('message-form', {
    props: ['messages'],
    data: function () {
        return {
            text: ''
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Write something" v-model="text" />' +
        '<input type="button" value="Save" v-on:click="save" />' +
        '</div>',
    methods: {
        save: function () {
            var message = { text: this.text };

            messageApi.save({}, message).then(result =>
                result.json().then(data => {
                    this.messages.push(data);
                    this.text = ''
                })
            )
        }
    }
});

Vue.component('message-row', {
    props: ['message'],
    template: '<div><i>({{ message.id }})</i>{{ message.text }}</div>'
});

Vue.component('messages-list', {
    props: ['messages'],
    template:
        '<div>' +
        '<message-form :messages = "messages" />' +
        '<message-row v-for="message in messages" :key="message.id" :message="message"/>' +
        '</div>',
    created: function() {
        var self = this; // сохраняем ссылку на текущий контекст
        messageApi.get().then(function (result) {
            result.json().then(function(data) {
                data.forEach(function(message) {
                    self.messages.push(message); // исправлено
                });
            });
        });
    }
});
var app = new Vue({
    el: '#app',
    template: '<messages-list :messages = "messages" />',
    data: {
        messages: []
    }
});