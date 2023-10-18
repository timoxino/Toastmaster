resource "google_pubsub_topic" "topic_compiled_questions" {
  name = "compiled_questions_topic"
}

resource "google_pubsub_subscription" "subscription_toastmaster" {
  name  = "toastmaster_subscription"
  topic = google_pubsub_topic.topic_compiled_questions.name
  ack_deadline_seconds = 600
}
