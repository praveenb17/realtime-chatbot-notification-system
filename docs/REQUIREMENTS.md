\# Requirements



\## MVP Scope (Phase 1)

\### Functional

\- Real-time 1:1 chat between two users

\- Real-time message delivery over WebSocket

\- Persist messages to DB

\- Fetch chat history (pagination)

\- Basic user presence: online/offline

\- Basic delivery status: SENT / DELIVERED



\### Non-Functional

\- Low latency delivery

\- Scale horizontally (stateless WebSocket gateway)

\- Fault tolerance for message processing

\- Logging for tracing requests/events



\## Phase 2 (Enhancements)

\- Group chat

\- Typing indicator

\- Read receipts

\- Rate limiting / spam control

\- Authentication + RBAC

\- Notifications (async) for missed messages

\- Observability metrics + dashboards



