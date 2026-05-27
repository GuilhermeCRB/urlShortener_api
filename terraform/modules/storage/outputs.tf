output "website_url" {
  value       = aws_s3_bucket_website_configuration.frontend_config.website_endpoint
  description = "The public endpoint for the frontend website"
}

output "bucket_id" {
  value = aws_s3_bucket.frontend.id
}