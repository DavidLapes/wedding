npm run --prefix ../frontend build
aws s3 sync ../frontend/build/ s3://terkaberedavida.cz
aws cloudfront create-invalidation --distribution-id E73O8TVBW4WB0 --paths "/*"
