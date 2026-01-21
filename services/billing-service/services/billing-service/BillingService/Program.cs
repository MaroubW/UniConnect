using BillingService.Services;
using SoapCore;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddSoapCore();
builder.Services.AddSingleton<IBillingService, BillingServiceImpl>();

var app = builder.Build();

// Configure the HTTP request pipeline.
app.UseRouting();

app.UseSoapEndpoint<IBillingService>("/BillingService.asmx", new SoapEncoderOptions());

app.Run();
