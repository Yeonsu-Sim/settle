var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        $('#add-participant').on('click', function() {
            $('#participants-list').append('<input type="text" name="participants[]" placeholder="참여자를 입력하세요"><br>');
        });
    },
    save: function () {
        var data = {
            product: $('#product').val(),
            amount: $('#amount').val(),
            payer: $('#payer').val(),
            participants: $("input[name='participants[]']").map(function(){return $(this).val();}).get(),
            dateTime: $('#dateTime').val(),
            memo: $('#memo').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/bills',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('영수증이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update: function () {
        var data = {
            product: $('#product').val(),
            amount: $('#amount').val(),
            payer: $('#payer').val(),
            participants: $("input[name='participants[]']").map(function(){return $(this).val();}).get(),
            dateTime: $('#dateTime').val(),
            memo: $('#memo').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/bills/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('영수증이 수정되었습니다.');
            window.location.href='/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/bills/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('영수증이 삭제되었습니다.');
            window.location.href='/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();