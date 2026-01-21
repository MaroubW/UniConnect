using System.ServiceModel;
using BillingService.Models;
using System.Collections.Generic;

namespace BillingService.Services
{
    [ServiceContract]
    public interface IBillingService
    {
        [OperationContract]
        List<Invoice> GetInvoicesByStudent(int studentId);

        [OperationContract]
        Invoice GetInvoice(int invoiceId);

        [OperationContract]
        void CreateInvoice(Invoice invoice);

        [OperationContract]
        void PayInvoice(int invoiceId, Payment payment);

        [OperationContract]
        List<Payment> GetPaymentsByInvoice(int invoiceId);
    }
}