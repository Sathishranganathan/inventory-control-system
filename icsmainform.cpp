#include "icsmainform.h"
#include "ui_icsmainform.h"
#include "goods.h"
#include "batch.h"
#include "category.h"
#include <QSqlRecord>
#include <QtGui>
#include <QTextCodec>

ICSMainForm::ICSMainForm(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::ICSMainForm)
{
    QTextCodec::setCodecForTr(QTextCodec::codecForLocale());
    ui->setupUi(this);
    QSqlTableModel *goodsModel = Goods::getTableModel();
    goodsModel->select();
    ui->goodsTableView->setModel(goodsModel);

    QSqlTableModel *batchModel = Batch::getTableModel();
    batchModel->select();
    ui->warehouseTableView->setModel(batchModel);

    QSqlTableModel *categoryModel = Category::getTableModel();
    categoryModel->select();
    ui->categoryTableView->setModel(categoryModel);

    bindCategory();
    bindGoods();



    // add status bar message
     statusBar()->showMessage("Status:Now you are in the mainform of Inventory Control System");



}

ICSMainForm::~ICSMainForm()
{
    delete ui;
}

void ICSMainForm::bindCategory()
{
    QSqlTableModel *model = Category::getTableModel();
    model->select();
    ui->comboBox_3->clear();//��֮ǰ���comobox������
    ui->comboBox_2->clear();

    for(int i=0; i <model->rowCount();i++)
    {


        ui->comboBox_3->addItem(model->record(i).value(1).toString(),model->record(i).value(0));//��nameΪ��ʾ�idΪ������
        ui->comboBox_2->addItem(model->record(i).value(1).toString(),model->record(i).value(0));
    }


}


void ICSMainForm::bindGoods()
{
    QSqlTableModel *model = Goods::getTableModel();
    model->select();
    ui->comboBox->clear();//��֮ǰ���comobox������
    ui->comboBox_5->clear();
    ui->comboBox_7->clear();


    for(int i=0; i <model->rowCount();i++)
    {


        ui->comboBox->addItem(model->record(i).value(1).toString(),model->record(i).value(0));//��nameΪ��ʾ�idΪ������
        ui->comboBox_5->addItem(model->record(i).value(1).toString(),model->record(i).value(0));
        ui->comboBox_7->addItem(model->record(i).value(1).toString(),model->record(i).value(0));
    }

}

void ICSMainForm::on_pushButton_5_clicked()
{
    QString name = ui->lineEdit_2->text();
       if(name.trimmed()!="")
       {
           Category category;
           category.setName(name);
           if(category.addCategory())
           {
               QMessageBox::warning(this,tr("�����ɹ�"),tr("Category��ӳɹ���"),QMessageBox::Yes);
               ui->lineEdit_2->clear();
               bindCategory();
           }
           else
           {

              QMessageBox::warning(this,tr("���ʧ��"),tr("δ֪ԭ�����categoryʧ�ܣ�"),QMessageBox::Yes);

           }
       }
       else
       {
           QMessageBox::warning(this,tr("���ʧ��"),tr("category����Ϊ�գ����ʧ�ܣ�"),QMessageBox::Yes);
       }


}

void ICSMainForm::on_pushButton_4_clicked()
{
    Goods *g=new Goods();
    g->setName(ui->lineEdit->text());
    g->setDepletionLine(ui->spinBox_3->value());
    g->setcatid((ui->comboBox_2->currentIndex())+1);
    if(g->addGoods()){
        QSqlTableModel *model = Goods::getTableModel();
        model->select();
        ui->goodsTableView->setModel(model);
        ui->goodsTableView->reset();
         qDebug()<<"ok!\n";
        }
}

void ICSMainForm::on_inboundSubmitBtn_clicked()
{
    Batch *b = new Batch();
    b->setBatchNumber(ui->spinBox_BatchNo_Inbound->text());
    b->setBTime(ui->dateTimeEdit_inbound->dateTime());


}
