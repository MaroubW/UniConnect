using System;

namespace BillingService.Models
{
    public class Invoice
    {
        public int Id { get; set; }
        public int StudentId { get; set; }
        public decimal Amount { get; set; }
        public DateTime DueDate { get; set; }
        public string Status { get; set; } // "Unpaid", "Paid"
        public DateTime CreatedDate { get; set; }
    }
}