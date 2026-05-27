# --- S3 Bucket for Frontend Hosting ---
resource "aws_s3_bucket" "frontend" {
  # Bucket names must be globally unique
  bucket = "${var.project_name}-frontend-ui-${var.environment}"

  tags = {
    Name        = "${var.project_name}-frontend"
    Environment = var.environment
  }
}

# --- Enable Static Website Hosting ---
resource "aws_s3_bucket_website_configuration" "frontend_config" {
  bucket = aws_s3_bucket.frontend.id

  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "index.html"
  }
}

# --- Public Access Configuration ---
# Step 1: Disable the default "Block Public Access" settings
resource "aws_s3_bucket_public_access_block" "frontend_access" {
  bucket = aws_s3_bucket.frontend.id

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

# Step 2: Set the Bucket Policy to allow public read access
resource "aws_s3_bucket_policy" "allow_public_read" {
  bucket = aws_s3_bucket.frontend.id

  # Dependent on the public access block being disabled first
  depends_on = [aws_s3_bucket_public_access_block.frontend_access]

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid       = "PublicReadGetObject"
        Effect    = "Allow"
        Principal = "*"
        Action    = "s3:GetObject"
        Resource  = "${aws_s3_bucket.frontend.arn}/*"
      },
    ]
  })
}