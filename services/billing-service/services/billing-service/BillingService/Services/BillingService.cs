using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using BillingService.Models;
using Microsoft.Extensions.Logging;

namespace BillingService.Services
{
    public class BillingServiceImpl : IBillingService
    {
        private readonly List<Invoice> _invoices = new List<Invoice>();
        private readonly List<Payment> _payments = new List<Payment>();
        private int _invoiceIdCounter = 1;
        private int _paymentIdCounter = 1;
        private readonly ILogger<BillingServiceImpl> _logger;

        public BillingServiceImpl(ILogger<BillingServiceImpl> logger)
        {
            _logger = logger;
        }

        public List<Invoice> GetInvoicesByStudent(int studentId)
        {
            try
            {
                _logger.LogInformation("Getting invoices for student {StudentId}", studentId);
                return _invoices.Where(i => i.StudentId == studentId).ToList();
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error getting invoices for student {StudentId}", studentId);
                throw new FaultException("Error retrieving invoices");
            }
        }

        public Invoice GetInvoice(int invoiceId)
        {
            try
            {
                _logger.LogInformation("Getting invoice {Id}", invoiceId);
                var invoice = _invoices.FirstOrDefault(i => i.Id == invoiceId);
                if (invoice == null)
                {
                    _logger.LogWarning("Invoice {Id} not found", invoiceId);
                    throw new FaultException("Invoice not found");
                }
                return invoice;
            }
            catch (FaultException)
            {
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error getting invoice {Id}", invoiceId);
                throw new FaultException("Error retrieving invoice");
            }
        }

        public void CreateInvoice(Invoice invoice)
        {
            try
            {
                _logger.LogInformation("Creating invoice for student {StudentId}", invoice.StudentId);
                invoice.Id = _invoiceIdCounter++;
                invoice.CreatedDate = DateTime.Now;
                invoice.Status = "Unpaid";
                _invoices.Add(invoice);
                _logger.LogInformation("Invoice created with ID {Id}", invoice.Id);
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error creating invoice");
                throw new FaultException("Error creating invoice");
            }
        }

        public void PayInvoice(int invoiceId, Payment payment)
        {
            try
            {
                _logger.LogInformation("Processing payment for invoice {Id}", invoiceId);
                var invoice = _invoices.FirstOrDefault(i => i.Id == invoiceId);
                if (invoice == null)
                {
                    _logger.LogWarning("Invoice {Id} not found for payment", invoiceId);
                    throw new FaultException("Invoice not found");
                }
                if (invoice.Status != "Unpaid")
                {
                    _logger.LogWarning("Invoice {Id} is not unpaid", invoiceId);
                    throw new FaultException("Invoice is not payable");
                }
                payment.Id = _paymentIdCounter++;
                payment.InvoiceId = invoiceId;
                payment.PaymentDate = DateTime.Now;
                _payments.Add(payment);
                // Simple logic: if payment covers the amount, mark as paid
                var totalPaid = _payments.Where(p => p.InvoiceId == invoiceId).Sum(p => p.Amount);
                if (totalPaid >= invoice.Amount)
                {
                    invoice.Status = "Paid";
                    _logger.LogInformation("Invoice {Id} marked as paid", invoiceId);
                }
                _logger.LogInformation("Payment processed for invoice {Id}", invoiceId);
            }
            catch (FaultException)
            {
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error processing payment for invoice {Id}", invoiceId);
                throw new FaultException("Error processing payment");
            }
        }

        public List<Payment> GetPaymentsByInvoice(int invoiceId)
        {
            try
            {
                _logger.LogInformation("Getting payments for invoice {Id}", invoiceId);
                return _payments.Where(p => p.InvoiceId == invoiceId).ToList();
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error getting payments for invoice {Id}", invoiceId);
                throw new FaultException("Error retrieving payments");
            }
        }
    }
}