output "distribution_domain_name" {
  value = local.fqdn
}

output "distribution_id" {
  value = aws_cloudfront_distribution.this.id
}
