#include "widget.h"
#include "ui_widget.h"
#include <QMessageBox>
#include <QSqlError>

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);
    model = new QSqlTableModel(this);
    model->setTable("Goods");
    model->setEditStrategy(QSqlTableModel::OnManualSubmit);
    model->select(); //ѡȡ�������������
    ui->tableView->setModel(model);
}

Widget::~Widget()
{
    delete ui;
}

void Widget::on_viewBtn_clicked()
{
    QString name = ui->lineEdit->text();
    model->setFilter(QObject::tr("name like '%%1%'").arg(name));
    model->select(); //��ʾ���
}

void Widget::on_editBnt_clicked()
{
    model->database().transaction(); //��ʼ�������
       if (model->submitAll()) {
           model->database().commit(); //�ύ
       } else {
           model->database().rollback(); //�ع�
           QMessageBox::warning(this, tr("tableModel"),
                                tr("���ݿ����: %1")
                                .arg(model->lastError().text()));
       }
}

void Widget::on_cancleBnt_clicked()
{
    model->revertAll();
}

void Widget::on_viewAllBnt_clicked()
{
    model->setTable("Goods");
    model->select();
}

void Widget::on_increaseBnt_clicked()
{
    model->setSort(0,Qt::AscendingOrder);
    model->select();
}

void Widget::on_decreaseBnt_clicked()
{
    model->setSort(0,Qt::DescendingOrder);
    model->select();
}

void Widget::on_delBnt_clicked()
{
    int curRow = ui->tableView->currentIndex().row();
    model->removeRow(curRow);
    int ok = QMessageBox::warning(this,tr("ɾ����ǰ��!"),tr("��ȷ��""ɾ����ǰ����"),
                                  QMessageBox::Yes,QMessageBox::No);
    if(ok == QMessageBox::No)
    {
        model->revertAll(); //�����ɾ��������
    }
    else model->submitAll(); //�����ύ�������ݿ���ɾ������
}

void Widget::on_addBnt_clicked()
{
    int rowNum = model->rowCount(); //��ñ������
    int id = 10;
    model->insertRow(rowNum); //���һ��
    model->setData(model->index(rowNum,0),id);
    //model->submitAll(); //����ֱ���ύ
}
