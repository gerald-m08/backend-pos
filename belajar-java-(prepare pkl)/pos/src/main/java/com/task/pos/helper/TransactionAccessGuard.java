package com.task.pos.helper;

import com.task.pos.entity.Transaction;
import com.task.pos.exception.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TransactionAccessGuard {

    private static final String ADMIN_AUTHORITY = "ROLE_ADMIN";

    public void assertCanAccess(Transaction transaction) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (isAdmin(authentication)) {
            return;
        }

        String username = authentication.getName();

        if (!transaction.getProcessedBy().getUsername().equals(username)) {
            throw new ForbiddenException("You are not allowed to access this transaction");
        }
    }

    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(ADMIN_AUTHORITY));
    }
}